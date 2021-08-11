import { Suspense, useEffect, useState } from "react";
import { useMutation, useQueryClient } from "react-query";
import { useHistory } from "react-router-dom";

import { ReviewerRegisterFormData } from "types/reviewer";

import { registerReviewer } from "apis/reviewer";
import FormProvider from "components/FormProvider/FormProvider";
import InputField from "components/FormProvider/InputField";
import SubmitButton from "components/FormProvider/SubmitButton";
import TextareaField from "components/FormProvider/TextareaField";
import SpecPicker from "components/Language/SpecPicker";
import Loading from "components/Loading/Loading";
import { Flex } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { QUERY_KEY } from "utils/constants/key";
import { ERROR_MESSAGE, PLACE_HOLDER, SUCCESS_MESSAGE } from "utils/constants/message";
import { PATH } from "utils/constants/path";
import { STANDARD } from "utils/constants/standard";
import reviewerRegisterValidators from "utils/validators/reviewerRegisterValidators";

interface Specs {
  [language: string]: string[];
}

const ReviewerRegister = () => {
  const { user } = useAuthContext();

  const [filterLanguage, setFilterLanguage] = useState<string | null>(null);
  const [specs, setSpecs] = useState<Specs>({});

  const history = useHistory();
  const queryClient = useQueryClient();

  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const mutation = useMutation((reviewerRegisterFormData: ReviewerRegisterFormData) =>
    revalidate(async () => {
      const response = await registerReviewer(reviewerRegisterFormData);

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });
      } else {
        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEWER_LIST);
        queryClient.invalidateQueries(QUERY_KEY.CHECK_MEMBER);

        toast(SUCCESS_MESSAGE.API.REVIEWER.REGISTER);
      }

      return response;
    })
  );

  if (mutation.isLoading) return <Loading />;

  useEffect(() => {
    if (user?.role === "TEACHER") {
      toast(ERROR_MESSAGE.AUTH.ALREADY_REGISTERED_REVIEWER, { type: "error" });
      history.push(PATH.MAIN);
    }
  }, []);

  return (
    <Flex css={{ flexDirection: "column" }}>
      <h2>리뷰어 등록</h2>
      <FormProvider
        submit={async ({ career, title, content }) => {
          const techSpecs = Object.entries(specs).map(([language, skills]) => ({ language, skills }));

          if (techSpecs.length === 0) {
            toast(ERROR_MESSAGE.VALIDATION.REVIEWER_REGISTER.TECH_SPEC, { type: "error" });

            return;
          }

          mutation.mutate({
            techSpecs: Object.entries(specs).map(([language, skills]) => ({ language, skills })),
            career,
            title,
            content,
          });

          history.push(PATH.MAIN);
        }}
        validators={reviewerRegisterValidators}
        css={{ marginTop: "1.25rem", width: "100%" }}
      >
        <SpecPicker
          filterLanguage={filterLanguage}
          specs={specs}
          onSetFilterLanguage={setFilterLanguage}
          onSetSpecs={setSpecs}
        />
        <Flex css={{ margin: "1.25rem 0 ", width: "100%" }}>
          <div css={{ flexGrow: 5, marginRight: "1.25rem" }}>
            <InputField
              name="title"
              labelText="타이틀"
              maxLength={STANDARD.REVIEWER_REGISTER.TITLE.MAX_LENGTH}
              placeholder={PLACE_HOLDER.REVIEWER_REGISTER.TITLE}
              required
            />
          </div>
          <div css={{ flexGrow: 1 }}>
            <InputField
              name="career"
              labelText="경력"
              type="number"
              min={STANDARD.REVIEWER_REGISTER.CAREER.MIN}
              max={STANDARD.REVIEWER_REGISTER.CAREER.MAX}
              placeholder={PLACE_HOLDER.REVIEWER_REGISTER.CAREER}
              required
            />
          </div>
        </Flex>
        <TextareaField
          name="content"
          labelText="소개"
          placeholder={PLACE_HOLDER.REVIEWER_REGISTER.CONTENT}
          maxLength={STANDARD.REVIEWER_REGISTER.CONTENT.MAX_LENGTH}
          required
          css={{ minHeight: "31.25rem" }}
        />
        <Flex css={{ margin: "1.25rem 0 2.5rem" }}>
          <SubmitButton css={{ marginLeft: "auto" }}>등록</SubmitButton>
        </Flex>
      </FormProvider>
    </Flex>
  );
};

export default ReviewerRegister;
