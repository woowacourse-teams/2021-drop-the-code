import { Link } from "react-router-dom";

import styled from "styled-components";

import { Reviewer } from "../../../types/reviewer";
import { COLOR } from "../../../utils/constants/color";
import Avatar from "../../shared/Avatar/Avatar";
import Chip from "../../shared/Chip/Chip";
import { Flex, FlexSpaceBetween } from "../../shared/Flexbox/Flexbox";

const ReviewerLink = styled(Link)`
  margin-bottom: 1.25rem;
`;

const Inner = styled(Flex)`
  width: 100%;
  padding: 1.25rem;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: 0.4375rem 0.375rem 0.375rem rgb(0 0 0 / 10%);
`;

const ChipWrapper = styled(Flex)`
  > div:not(:last-child) {
    margin-right: 0.625rem;
  }
`;

const Title = styled.p`
  font-weight: 900;
  font-size: 1.125rem;
`;

const ReviewerCard = ({ id, imageUrl, career, sumReviewCount, averageReviewTime, title, techSpec }: Reviewer) => (
  <ReviewerLink to={`/reviewer/${id}`}>
    <Inner>
      <Avatar imageUrl={imageUrl} width="6.25rem" height="6.25rem" css={{ marginRight: "1.875rem" }} />
      <FlexSpaceBetween css={{ flexDirection: "column", flex: 1 }}>
        <ChipWrapper>
          <Chip themeColor="career" css={{ fontSize: "0.875rem" }}>
            <p>경력 {career}년차</p>
          </Chip>
          <Chip themeColor="count" css={{ fontSize: "0.875rem" }}>
            <p>누적 리뷰 {sumReviewCount}회</p>
          </Chip>
          {!!averageReviewTime && (
            <Chip themeColor="averageReview" css={{ fontSize: "0.875rem" }}>
              <p>평균 답변 {averageReviewTime}일</p>
            </Chip>
          )}
        </ChipWrapper>
        <Flex>
          <Title>{title}</Title>
        </Flex>
        <Flex css={{ flexDirection: "column" }}>
          <p css={{ color: COLOR.GRAY_500, paddingBottom: "0.3125rem" }}>
            {`언어: ${[...techSpec.languages.map((language) => language.name)].join(", ")}`}
          </p>
          <p css={{ color: COLOR.GRAY_500 }}>
            {`기술 스택: ${[...techSpec.skills.map((skill) => skill.name)].join(", ")}`}
          </p>
        </Flex>
      </FlexSpaceBetween>
    </Inner>
  </ReviewerLink>
);

export default ReviewerCard;
