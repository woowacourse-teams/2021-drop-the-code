import { useState } from "react";

import styled from "styled-components";

import DarkStar from "assets/dark-star.svg";
import LightStar from "assets/light-star.svg";
import FormProvider from "components/FormProvider/FormProvider";
import SubmitButton from "components/FormProvider/SubmitButton";
import TextareaField from "components/FormProvider/TextareaField";
import Avatar from "components/shared/Avatar/Avatar";
import { Flex, FlexCenter, FlexSpaceBetween } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";
import { PLACE_HOLDER } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";
import reviewFeedBackValidators from "utils/validators/reviewFeedBackValidators";

export interface Props {
  reviewId: number;
  teacherProfile: {
    id: number;
    name: string;
    imageUrl: string;
  };
}

const Inner = styled(FlexCenter)`
  background-color: ${COLOR.WHITE};
  flex-direction: column;
  width: 500px;
  padding: 1.25rem;
`;

const Grade = styled(FlexCenter)`
  width: 100%;
  position: relative;
  margin-bottom: 1.25rem;
`;

const Title = styled.p`
  font-size: 24px;
  margin-bottom: 2.5rem;
`;

const Range = styled.input`
  width: 50%;
  height: 100%;
  position: absolute;
  padding: 0 7px;
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
  width: 50%;
`;

const ReviewFeedback = ({ reviewId, teacherProfile }: Props) => {
  const { id, name, imageUrl } = teacherProfile;

  const [star, setStar] = useState(STANDARD.REVIEW_FEEDBACK.MIN_GRADE);

  return (
    <Inner>
      <Title>{name}님의 리뷰 평가하기</Title>
      <Avatar imageUrl={imageUrl} width="6rem" css={{ marginBottom: "1.25rem" }} />
      <FormProvider
        submit={({ star, content }) => {
          /*
          request
        */
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
              index + 1 <= star ? <LightStar width={40} key={index} /> : <DarkStar width={40} key={index} />
            )}
          </Star>
        </Grade>
        <TextareaField
          name="content"
          placeholder={PLACE_HOLDER.REVIEW_FEEDBACK.CONTENT}
          css={{ minHeight: "15.625rem" }}
        />
        <Flex css={{ margin: "1.25rem 0 2.5rem" }}>
          <SubmitButton css={{ marginLeft: "auto" }}>제출</SubmitButton>
        </Flex>
      </FormProvider>
    </Inner>
  );
};

export default ReviewFeedback;
