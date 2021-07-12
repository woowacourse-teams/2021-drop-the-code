import { Link } from "react-router-dom";

import styled from "styled-components";

import { COLOR } from "../../utils/constants/color";
import Avatar from "../shared/Avatar/Avatar";
import Chip from "../shared/Chip/Chip";
import { Flex, FlexSpaceBetween } from "../shared/Flexbox/Flexbox";

export interface Props {
  id: number;
  avatarUrl: string;
  career: number;
  reviewCount: number;
  averageResponseTime: number | null;
  title: string;
  languages: string[];
  skills: string[];
}

const Inner = styled(Flex)`
  width: 100%;
  padding: 1.875rem;
  border-radius: ${({ theme }) => theme.shape.rounded};
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

const ReviewerCard = ({
  id,
  avatarUrl,
  career,
  reviewCount,
  averageResponseTime,
  title,
  languages,
  skills,
  ...props
}: Props) => (
  <Link to={`/reviewer/${id}`}>
    <Inner {...props}>
      <Avatar imageUrl={avatarUrl} width="6.25rem" height="6.25rem" shape="rounded" css={{ marginRight: "1.875rem" }} />
      <FlexSpaceBetween css={{ flexDirection: "column", flex: 1 }}>
        <ChipWrapper>
          <Chip color={COLOR.WHITE} backgroundColor={COLOR.INDIGO_400}>
            <p>{career}년 이내 경력</p>
          </Chip>
          <Chip color={COLOR.WHITE} backgroundColor={COLOR.PURPLE_400}>
            <p>누적 리뷰 {reviewCount}회</p>
          </Chip>
          {averageResponseTime && (
            <Chip color={COLOR.WHITE} backgroundColor={COLOR.YELLOW_400}>
              <p>평균 {averageResponseTime}일내 리뷰</p>
            </Chip>
          )}
        </ChipWrapper>
        <Flex>
          <Title>{title}</Title>
        </Flex>
        <Flex>
          <p>{[...languages, ...skills].join(" • ")}</p>
        </Flex>
      </FlexSpaceBetween>
    </Inner>
  </Link>
);

export default ReviewerCard;
