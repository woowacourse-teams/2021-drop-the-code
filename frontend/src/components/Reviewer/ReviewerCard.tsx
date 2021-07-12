import { Link } from "react-router-dom";

import styled from "styled-components";

import { COLOR } from "../../utils/constants/color";
import Chip from "../Chip/Chip";
import Avatar from "../shared/Avatar/Avatar";
import { Flex, FlexSpaceBetween } from "../shared/Flexbox/Flexbox";

export interface Props {
  id: number;
  avatarUrl: string;
  career: number;
  reviewCount: number;
  averageResponseTime: number;
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
    margin-right: 10px;
  }
`;

const Title = styled.p`
  font-weight: 600;
`;

const ReviewerCard = ({ id, avatarUrl, career, reviewCount, averageResponseTime, title, languages, skills }: Props) => (
  <Inner>
    <Link to={`/reviewer/${id}`}>
      <Avatar imageUrl={avatarUrl} width="6.25rem" height="6.25rem" shape="rounded" css={{ marginRight: "30px" }} />
      <FlexSpaceBetween css={{ flexDirection: "column", flex: 1 }}>
        <ChipWrapper>
          <Chip color={COLOR.WHITE} backgroundColor={COLOR.BLUE_400}>
            <p>{career}년 이내 경력</p>
          </Chip>
          <Chip color={COLOR.WHITE} backgroundColor={COLOR.PURPLE_300}>
            <p>누적 리뷰 {reviewCount}회</p>
          </Chip>
          <Chip color={COLOR.WHITE} backgroundColor={COLOR.YELLOW_200}>
            <p>평균 {averageResponseTime}일내 리뷰</p>
          </Chip>
        </ChipWrapper>
        <Flex>
          <Title>{title}</Title>
        </Flex>
        <Flex>
          <p>{[...languages, ...skills].join(" • ")}</p>
        </Flex>
      </FlexSpaceBetween>
    </Link>
  </Inner>
);

export default ReviewerCard;
