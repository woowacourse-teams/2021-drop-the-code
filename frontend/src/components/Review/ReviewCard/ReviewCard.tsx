import { Link } from "react-router-dom";

import styled from "styled-components";
import { Progress as ProgressShape, Review } from "types/review";

import { Flex, FlexAlignCenter } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";
import { PROGRESS } from "utils/constants/progress";
import { toPassedTimeString } from "utils/formatter";

const ReviewLink = styled(Link)`
  margin-bottom: 1.25rem;
`;

const Inner = styled(FlexAlignCenter)`
  width: 100%;
  min-height: 9.375rem;
  padding: 1.25rem;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
  :hover {
    background-color: ${COLOR.GRAY_100};
  }
`;

interface ProgressProps {
  progress: ProgressShape;
}

const Progress = styled.div<ProgressProps>`
  width: 4.375rem;
  font-weight: 900;
  color: ${({ theme, progress }) =>
    progress !== "FINISHED" && progress !== "DENIED" ? theme.common.color.primary : COLOR.BLACK};
  margin-right: 1.25rem;
`;

const ContentWrapper = styled(Flex)`
  width: calc(100% - 8.75rem);
  flex-direction: column;
  margin-right: 1.25rem;
`;

const Title = styled.p`
  font-weight: 900;
  font-size: 1.125rem;
  margin-bottom: 0.625rem;
`;

const Content = styled.p`
  color: ${COLOR.GRAY_500};
  font-size: 1rem;
  line-height: 1.25rem;

  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
`;

const RequestTime = styled.div`
  width: 4.375rem;
  color: ${COLOR.GRAY_500};
  text-align: right;
`;

export const progress = {
  PENDING: "대기중",
  DENIED: "거절됨",
  ON_GOING: "진행중",
  TEACHER_COMPLETED: "확인중",
  FINISHED: "완료",
};

const ReviewCard = ({ id, title, content, progress, createdAt }: Review) => (
  <ReviewLink to={`/review/${id}`}>
    <Inner>
      <Progress progress={progress}>
        <p>{PROGRESS[progress]}</p>
      </Progress>
      <ContentWrapper>
        <Title>{title}</Title>
        <Content>{content}</Content>
      </ContentWrapper>
      <RequestTime>
        <p>{toPassedTimeString(...createdAt)}</p>
      </RequestTime>
    </Inner>
  </ReviewLink>
);

export default ReviewCard;
