import { useState } from "react";

import styled from "styled-components";
import { Review } from "types/review";

import DarkStar from "assets/dark-star.svg";
import LightStar from "assets/light-star.svg";
import FormProvider from "components/FormProvider/FormProvider";
import SubmitButton from "components/FormProvider/SubmitButton";
import TextareaField from "components/FormProvider/TextareaField";
import Avatar from "components/shared/Avatar/Avatar";
import { Flex, FlexCenter, FlexSpaceBetween } from "components/shared/Flexbox/Flexbox";
import useModalContext from "hooks/useModalContext";
import useReview from "hooks/useReview";
import { COLOR } from "utils/constants/color";
import { ALT, PLACE_HOLDER } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";
import reviewFeedBackValidators from "utils/validators/reviewFeedBackValidators";

const Inner = styled(FlexCenter)`
  background-color: ${COLOR.WHITE};
  flex-direction: column;
  width: 25rem;
  padding: 1.25rem;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
`;

const Grade = styled(FlexCenter)`
  width: 100%;
  position: relative;
  margin-bottom: 1.25rem;
`;

const Title = styled.p`
  font-size: 20px;
  margin-bottom: 1.25rem;
`;

const Range = styled.input`
  width: 40%;
  height: 100%;
  position: absolute;
  padding: 0 0.4375rem;
  cursor: pointer;
  background: transparent;
  -webkit-appearance: none;

  ::-webkit-slider-runnable-track {
    cursor: pointer;
  }
  ::-webkit-slider-thumb {
    margin-top: -0.625rem;
    width: 0.9375rem;
    height: 1.25rem;
    border-radius: 0.3125rem;
    cursor: pointer;
    -webkit-appearance: none;
  }
`;

const Star = styled(FlexSpaceBetween)`
  width: 40%;
`;

const ReviewFeedback = ({ id: reviewId, teacherProfile }: Pick<Review, "id" | "teacherProfile">) => {
  const { name, imageUrl } = teacherProfile;
  const { close } = useModalContext();
  const {
    mutation: { finish },
  } = useReview(reviewId);
  const [star, setStar] = useState(STANDARD.REVIEW_FEEDBACK.MIN_GRADE);

  return (
    <Inner>
      <Title>{name}님의 리뷰 평가하기</Title>
      <Avatar
        imageUrl={imageUrl}
        width="6rem"
        css={{ marginBottom: "1.25rem" }}
        alt={`${name}${ALT.REVIEWER_PROFILE_AVATAR}`}
      />
      <FormProvider
        submit={({ content }) => {
          finish({ star, comment: content });
          close();
        }}
        validators={reviewFeedBackValidators}
      >
        <Grade>
          <Range
            type="range"
            value={star}
            min={STANDARD.REVIEW_FEEDBACK.MIN_GRADE}
            max={STANDARD.REVIEW_FEEDBACK.MAX_GRADE}
            step={1}
            onChange={({ target }) => {
              const { valueAsNumber } = target;

              setStar(valueAsNumber);
            }}
          />
          <Star>
            {[...Array(STANDARD.REVIEW_FEEDBACK.MAX_GRADE)].map((_, index) =>
              index + 1 <= star ? <LightStar width={30} key={index} /> : <DarkStar width={30} key={index} />
            )}
          </Star>
        </Grade>
        <TextareaField
          name="content"
          placeholder={PLACE_HOLDER.REVIEWER_FEEDBACK.CONTENT}
          css={{ minHeight: "9.375rem" }}
        />
        <Flex css={{ marginTop: "1.25rem" }}>
          <SubmitButton css={{ marginLeft: "auto" }}>제출</SubmitButton>
        </Flex>
      </FormProvider>
    </Inner>
  );
};

export default ReviewFeedback;
