import styled from "styled-components";
import { Review } from "types/review";

import FormProvider from "components/FormProvider/FormProvider";
import InputField from "components/FormProvider/InputField";
import SubmitButton from "components/FormProvider/SubmitButton";
import TextareaField from "components/FormProvider/TextareaField";
import { Flex } from "components/shared/Flexbox/Flexbox";
import useModalContext from "hooks/useModalContext";
import useReview from "hooks/useReview";
import { COLOR } from "utils/constants/color";
import { PLACE_HOLDER } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";
import reviewRequestValidators from "utils/validators/reviewRequestValidators";

const Inner = styled.div`
  background-color: ${COLOR.WHITE};
  width: 40.625rem;
  margin: 0 auto;
  padding: 1.25rem;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
`;

interface Props {
  review: Review;
}

const ReviewEdit = ({ review }: Props) => {
  const {
    mutation: { edit },
  } = useReview(review.id);
  const { close } = useModalContext();

  const { studentProfile, teacherProfile } = review;

  return (
    <Inner>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600, margin: "1.25rem 0 2.5rem", textAlign: "center" }}>리뷰 수정</h2>
      <FormProvider
        submit={async ({ title, prUrl, content }) => {
          await edit({ studentId: studentProfile.id, teacherId: teacherProfile.id, title, prUrl, content });

          close();
        }}
        validators={reviewRequestValidators}
        css={{ marginTop: "1.25rem", width: "100%" }}
      >
        <Flex css={{ margin: "1.25rem 0 ", width: "100%" }}>
          <InputField
            name="title"
            labelText="타이틀"
            maxLength={STANDARD.REVIEW_REQUEST.TITLE.MAX_LENGTH}
            placeholder={PLACE_HOLDER.REVIEW_REQUEST.TITLE}
            initialValue={review.title}
            required
          />
        </Flex>
        <Flex css={{ margin: "1.25rem 0 ", width: "100%" }}>
          <InputField
            name="prUrl"
            labelText="Pull Request주소"
            maxLength={STANDARD.REVIEW_REQUEST.PR_URL.MAX_LENGTH}
            placeholder={PLACE_HOLDER.REVIEW_REQUEST.PR_URL}
            initialValue={review.prUrl}
            required
          />
        </Flex>
        <TextareaField
          name="content"
          labelText="본문"
          placeholder={PLACE_HOLDER.REVIEW_REQUEST.CONTENT}
          maxLength={STANDARD.REVIEW_REQUEST.CONTENT.MAX_LENGTH}
          initialValue={review.content}
          required
          css={{ minHeight: "12.5rem" }}
        />
        <Flex css={{ margin: "1.25rem 0 2.5rem" }}>
          <SubmitButton themeColor="primary" css={{ marginLeft: "auto" }}>
            수정
          </SubmitButton>
        </Flex>
      </FormProvider>
    </Inner>
  );
};

export default ReviewEdit;
