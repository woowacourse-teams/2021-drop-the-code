import { Suspense, useState } from "react";
import { useMutation } from "react-query";
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
import useRevalidate from "hooks/useRevalidate";
import { PLACE_HOLDER } from "utils/constants/message";
import { PATH } from "utils/constants/path";
import { LAYOUT } from "utils/constants/size";
import { STANDARD } from "utils/constants/standard";
import reviewerRegisterValidators from "utils/validators/reviewerRegisterValidators";
// import useAuthContext from "hooks/useAuthContext";

interface Specs {
  [language: string]: string[];
}

const ReviewerRegister = () => {
  const [filterLanguage, setFilterLanguage] = useState<string | null>(null);
  const [specs, setSpecs] = useState<Specs>({});

  const { revalidate } = useRevalidate();

  // const { user } = useAuthContext();
  const history = useHistory();

  const mutation = useMutation(
    (reviewerRegisterFormData: ReviewerRegisterFormData) =>
      revalidate(() => registerReviewer(reviewerRegisterFormData)),
    {
      onSuccess: () => {
        history.push(PATH.MAIN);
      },
      onError: () => {
        history.push(PATH.MAIN);
        alert("에러");
      },
    }
  );

  if (mutation.isLoading) return <Loading />;

  // TODO 반복되는 메인 컴포넌트 + 테마로 관리하기
  return (
    <>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600 }}>리뷰어 등록</h2>
      <FormProvider
        submit={async ({ career, title, content }) => {
          const techSpecs = Object.entries(specs).map(([language, skills]) => ({ language, skills }));

          if (techSpecs.length === 0) {
            alert("기술을 선택해주세요");

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
        <Suspense fallback={<Loading />}>
          <SpecPicker
            filterLanguage={filterLanguage}
            specs={specs}
            onSetFilterLanguage={setFilterLanguage}
            onSetSpecs={setSpecs}
          />
        </Suspense>
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
          <SubmitButton themeColor="primary" shape="rounded" css={{ marginLeft: "auto" }}>
            등록
          </SubmitButton>
        </Flex>
      </FormProvider>
    </>
  );
};

export default ReviewerRegister;
