import { Link } from "react-router-dom";

import styled from "styled-components";

import notification from "assets/notification.png";
import Confirm from "components/Confirm/Confirm";
import Button from "components/shared/Button/Button";
import ContentBox from "components/shared/ContentBox/ContentBox";
import { FlexAlignCenter, Flex, FlexCenter, FlexSpaceBetween } from "components/shared/Flexbox/Flexbox";
import useModalContext from "hooks/useModalContext";
import useReview from "hooks/useReview";
import { COLOR } from "utils/constants/color";
import { ALT } from "utils/constants/message";

import ReviewEdit from "../ReviewEdit/ReviewEdit";
import ReviewFeedback from "../ReviewFeedback/ReviewFeedback";

const Title = styled.p`
  font-weight: 900;
`;

const PrUrl = styled.a`
  font-weight: 900;
  color: ${({ theme }) => theme.common.color.primary};
  margin-bottom: 0.625rem;
`;

const Info = styled(FlexSpaceBetween)`
  width: 34.375rem;
  font-size: 14px;
  color: ${COLOR.GRAY_500};
`;

const Profile = styled(FlexAlignCenter)`
  font-size: 14px;
  color: ${COLOR.GRAY_500};
`;

const RequestDetail = styled.p`
  margin-left: 0.625rem;
  display: inline-block;
`;
interface Props {
  reviewId: number;
}

const ReviewInfoContainer = ({ reviewId }: Props) => {
  const { open } = useModalContext();

  const { state, computed, mutation } = useReview(reviewId);

  if (state === null || computed === null) return null;

  const { review } = state;
  const { status, role, progress, nextProgress } = computed;
  const { cancel, deny, accept, complete } = mutation;

  const { isPending, isDenied, isOnGoing, isTeacherCompleted, isFinished } = status;
  const { isStudent, isTeacher, isAnonymous } = role;

  return (
    <>
      <ContentBox
        title={
          <FlexAlignCenter>
            <Flex css={{ width: "100%", flex: "1", flexDirection: "column", paddingRight: "1.25rem" }}>
              <Title css={{ marginBottom: "1.25rem" }}>{review.title}</Title>
              <Info>
                <Profile>
                  요청자:
                  <RequestDetail>{review.studentProfile.name}</RequestDetail>
                </Profile>
                <Link to={`/reviewer/${review.teacherProfile.id}`}>
                  <Profile>
                    리뷰어:
                    <RequestDetail>{review.teacherProfile.name}</RequestDetail>
                  </Profile>
                </Link>
                <div>
                  요청일:
                  <RequestDetail>{review.createdAt.join(".")}</RequestDetail>
                </div>
              </Info>
            </Flex>
            <p>{progress}</p>
          </FlexAlignCenter>
        }
      >
        <Flex css={{ flexDirection: "column" }}>
          <p css={{ fontSize: "14px", marginBottom: "3.125rem", minHeight: "15.625rem" }}>{review.content}</p>
          <Flex>
            <PrUrl href={review.prUrl} target="_blank" rel="noopener">
              PR 링크 바로가기
            </PrUrl>
            {isPending && isStudent && (
              <Flex css={{ marginLeft: "auto" }}>
                <Button
                  themeColor="secondary"
                  css={{ marginRight: "0.625rem" }}
                  onClick={() => {
                    open(<ReviewEdit review={review} />);
                  }}
                >
                  수정
                </Button>
                <Button
                  themeColor="secondary"
                  onClick={() => {
                    open(
                      <Confirm
                        title="삭제하시겠습니까?"
                        onConfirm={() => {
                          cancel();
                        }}
                      />
                    );
                  }}
                >
                  삭제
                </Button>
              </Flex>
            )}
          </Flex>
        </Flex>
      </ContentBox>
      <img
        src={notification}
        alt={ALT.REVIEW_DETAIL_NOTIFICATION}
        css={{ width: "21.875rem", marginBottom: "3.125rem" }}
      />
      <FlexCenter css={{ marginBottom: "6.25rem" }}>
        {!isDenied && !isFinished && !isAnonymous && nextProgress && (
          <Button
            disabled={(isStudent && isOnGoing) || (isTeacher && isTeacherCompleted)}
            onClick={() => {
              if (isOnGoing && isTeacher) complete();
              if (isTeacherCompleted && isStudent) {
                open(<ReviewFeedback id={review.id} teacherProfile={review.teacherProfile} />);
              }
            }}
          >
            {nextProgress}
          </Button>
        )}
        {isPending && isTeacher && (
          <>
            <Button
              css={{ marginRight: "0.625rem" }}
              onClick={() => {
                open(
                  <Confirm
                    title="거절하시겠습니까?"
                    onConfirm={() => {
                      deny();
                    }}
                  />
                );
              }}
            >
              거절
            </Button>
            <Button
              onClick={() => {
                open(
                  <Confirm
                    title="수락하시겠습니까?"
                    onConfirm={() => {
                      accept();
                    }}
                  />
                );
              }}
            >
              수락
            </Button>
          </>
        )}
      </FlexCenter>
    </>
  );
};

export default ReviewInfoContainer;
