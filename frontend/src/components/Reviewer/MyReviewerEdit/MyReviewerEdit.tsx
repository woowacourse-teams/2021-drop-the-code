import { Suspense, useState } from "react";
import { useMutation, useQueryClient } from "react-query";

import styled from "styled-components";
import { Reviewer, ReviewerRegisterFormData } from "types/reviewer";

import { editReviewer } from "apis/reviewer";
import FormProvider from "components/FormProvider/FormProvider";
import InputField from "components/FormProvider/InputField";
import SubmitButton from "components/FormProvider/SubmitButton";
import TextareaField from "components/FormProvider/TextareaField";
import SpecPicker from "components/Language/SpecPicker";
import Loading from "components/Loading/Loading";
import { Flex } from "components/shared/Flexbox/Flexbox";
import useModalContext from "hooks/useModalContext";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { COLOR } from "utils/constants/color";
import { QUERY_KEY } from "utils/constants/key";
import { ERROR_MESSAGE, PLACE_HOLDER, SUCCESS_MESSAGE } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";
import reviewerRegisterValidators from "utils/validators/reviewerRegisterValidators";

const Inner = styled.div`
  padding: 1.25rem;
  flex-direction: column;
  width: 40.625rem;
  background-color: ${COLOR.WHITE};
`;

interface Props {
  reviewer: Reviewer;
}

interface Specs {
  [language: string]: string[];
}

const MyReviewerEdit = ({ reviewer }: Props) => {
  const [filterLanguage, setFilterLanguage] = useState<string | null>(null);
  const [specs, setSpecs] = useState<Specs>({});

  const { revalidate } = useRevalidate();
  const { close } = useModalContext();
  const toast = useToastContext();

  const queryClient = useQueryClient();

  const mutation = useMutation((reviewerEditFormData: ReviewerRegisterFormData) =>
    revalidate(async () => {
      const response = await editReviewer(reviewerEditFormData);

      if (!response.isSuccess) {
        toast(response.error.message);
      } else {
        close();
        toast(SUCCESS_MESSAGE.API.REVIEWER.EDIT);

        queryClient.invalidateQueries([QUERY_KEY.GET_REVIEWER, reviewer.id]);
        queryClient.invalidateQueries(QUERY_KEY.CHECK_MEMBER);
      }

      return response;
    })
  );

  if (mutation.isLoading) return <Loading />;

  return (
    <Inner>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600, margin: "1.25rem 0 2.5rem", textAlign: "center" }}>
        리뷰어 정보 수정
      </h2>
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
              initialValue={reviewer.title}
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
              initialValue={reviewer.career}
              required
            />
          </div>
        </Flex>
        <TextareaField
          name="content"
          labelText="소개"
          placeholder={PLACE_HOLDER.REVIEWER_REGISTER.CONTENT}
          maxLength={STANDARD.REVIEWER_REGISTER.CONTENT.MAX_LENGTH}
          initialValue={reviewer.content}
          required
          css={{ minHeight: "12.5rem" }}
        />
        <Flex css={{ marginTop: "1.25rem" }}>
          <SubmitButton css={{ marginLeft: "auto" }}>확인</SubmitButton>
        </Flex>
      </FormProvider>
    </Inner>
  );
};

export default MyReviewerEdit;
