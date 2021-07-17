import { Link } from "react-router-dom";

import styled from "styled-components";

import { Reviewer } from "../../types/reviewer";
import Avatar from "../shared/Avatar/Avatar";
import Chip from "../shared/Chip/Chip";
import { Flex, FlexSpaceBetween } from "../shared/Flexbox/Flexbox";

const Inner = styled(Flex)`
  width: 100%;
  padding: 1.875rem;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: 11px 11px 30px 5px rgb(0 0 0 / 10%);
`;

const ChipWrapper = styled(Flex)`
  > div:not(:last-child) {
    margin-right: 0.625rem;
  }
`;

const Title = styled.p`
  font-weight: 600;
`;

const ReviewerCard = ({ id, imageUrl, career, sumReviewCount, averageReviewTime, title, techSpec }: Reviewer) => (
  <Link to={`/reviewer/${id}`}>
    <Inner>
      <Avatar imageUrl={imageUrl} width="6.25rem" height="6.25rem" shape="rounded" css={{ marginRight: "1.875rem" }} />
      <FlexSpaceBetween css={{ flexDirection: "column", flex: 1 }}>
        <ChipWrapper>
          <Chip themeColor="career">
            <p>{career}년 이내 경력</p>
          </Chip>
          <Chip themeColor="count">
            <p>누적 리뷰 {sumReviewCount}회</p>
          </Chip>
          {!!averageReviewTime && (
            <Chip themeColor="averageReview">
              <p>평균 {averageReviewTime}일내 리뷰</p>
            </Chip>
          )}
        </ChipWrapper>
        <Flex>
          <Title>{title}</Title>
        </Flex>
        <Flex>
          <p>
            {[
              ...techSpec.languages.map((language) => language.name),
              ...([] as string[]).concat(...techSpec.skills.map((skill) => skill.name)),
            ].join(" • ")}
          </p>
        </Flex>
      </FlexSpaceBetween>
    </Inner>
  </Link>
);

export default ReviewerCard;
