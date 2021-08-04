import { useMutation, useQuery, useQueryClient } from "react-query";

import styled from "styled-components";

import { getReview, patchReviewProgress } from "apis/review";
import Notification from "assets/notification.png";
import Confirm from "components/Confirm/Confirm";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import ContentBox from "components/shared/ContentBox/ContentBox";
import { FlexAlignCenter, Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useModalContext from "hooks/useModalContext";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { COLOR } from "utils/constants/color";
import { QUERY_KEY } from "utils/constants/key";
import { ALT, SUCCESS_MESSAGE } from "utils/constants/message";

import ReviewAmmend from "../ReviewAmmend/ReviewAmmend";
import ReviewFeedback from "../ReviewFeedback/ReviewFeedback";
import ReviewRequest from "../ReviewRequest/ReviewRequest";

const Title = styled.p`
  font-weight: 900;
`;

const PrUrl = styled.a`
  font-weight: 900;
  color: ${({ theme }) => theme.common.color.primary};
`;

const Profile = styled(FlexAlignCenter)`
  font-size: 14px;
  color: ${COLOR.GRAY_500};
`;

interface Props {
  reviewId: number;
}

const ReviewInfoContainer = ({ reviewId }: Props) => {
  const { user } = useAuthContext();
  const toast = useToastContext();
  const { open } = useModalContext();
  const { revalidate } = useRevalidate();

  const queryClient = useQueryClient();

  const { data } = useQuery([QUERY_KEY.GET_REVIEW, reviewId], async () => {
    const response = await getReview(reviewId);

    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  const mutation = useMutation(({ id, progress }: { id: number; progress: string }) => {
    return revalidate(async () => {
      const response = await patchReviewProgress(id, progress);

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });
      } else {
        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);

        toast(SUCCESS_MESSAGE.API.REVIEW.PATCH_PROGRESS);
      }

      return response;
    });
  });

  if (!data) return <></>;

  const currentProgress = data.progress;
  const isFinished = currentProgress === "FINISHED";

  const isStudent = data.studentProfile.id === user?.id;
  const isTeacher = data.teacherProfile.id === user?.id;
  const isAnonymous = !(isStudent || isTeacher);

  const nextProgressData = {
    ON_GOING: { buttonText: "리뷰 종료", progress: "complete", isStudentTurn: false },
    TEACHER_COMPLETED: { buttonText: "리뷰 완료", progress: "finish", isStudentTurn: true },
    FINISHED: { buttonText: "리뷰 완료", progress: "finish", isStudentTurn: false },
  };

  const progressText = {
    ON_GOING: "리뷰 준비중",
    TEACHER_COMPLETED: "확인중",
    FINISHED: "완료",
  };

  const nextProgress = nextProgressData[currentProgress].progress;
  const progress = progressText[currentProgress];

  const buttonText = nextProgressData[currentProgress].buttonText;
  const disabled = nextProgressData[currentProgress].isStudentTurn ? isTeacher : isStudent;

  return (
    <>
      <ContentBox
        title={
          <FlexAlignCenter>
            <Flex css={{ width: "100%", flex: "1", flexDirection: "column", paddingRight: "1.25rem" }}>
              <Title css={{ marginBottom: "1.25rem" }}>{data.title}</Title>
              <Profile>
                <Avatar
                  imageUrl={data.studentProfile.imageUrl}
                  width="1.875rem"
                  height="1.875rem"
                  css={{ marginRight: "1.25rem" }}
                />
                <p css={{ marginRight: "1.25rem" }}>{data.studentProfile.name}</p>
                <p css={{ width: "5.625rem" }}>{data.createdAt.join(".")}</p>
              </Profile>
            </Flex>
            <p>{progress}</p>
          </FlexAlignCenter>
        }
      >
        <Flex css={{ flexDirection: "column" }}>
          <p css={{ fontSize: "14px", marginBottom: "3.125rem", minHeight: "15.625rem" }}>{data.content}</p>
          <Flex>
            <PrUrl href={data.prUrl} target="_blank" rel="noopener">
              PR 링크 바로가기
            </PrUrl>
            <Flex css={{ marginLeft: "auto" }}>
              <Button
                themeColor="secondary"
                css={{ marginRight: "0.625rem" }}
                onClick={() => {
                  open(<ReviewAmmend review={data} />);
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
                        alert("삭제");
                      }}
                    />
                  );
                }}
              >
                삭제
              </Button>
            </Flex>
          </Flex>
        </Flex>
      </ContentBox>
      <img
        src={Notification}
        alt={ALT.REVIEW_DETAIL_NOTIFICATION}
        css={{ width: "21.875rem", marginBottom: "3.125rem" }}
      />
      <FlexCenter css={{ marginBottom: "6.25rem" }}>
        {!isFinished && !isAnonymous && buttonText && (
          <Button
            disabled={disabled}
            onClick={() => {
              if (currentProgress === "TEACHER_COMPLETED" && isStudent) {
                open(<ReviewFeedback reviewId={reviewId} teacherProfile={data.teacherProfile} />);

                return;
              }

              if (isFinished || isAnonymous || disabled) return;

              mutation.mutate({ id: reviewId, progress: nextProgress });
            }}
          >
            {buttonText}
          </Button>
        )}
        <Button
          css={{ marginRight: "0.625rem" }}
          onClick={() => {
            open(
              <Confirm
                title="거절하시겠습니까?"
                onConfirm={() => {
                  alert("거절");
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
                  alert("수락");
                }}
              />
            );
          }}
        >
          수락
        </Button>
      </FlexCenter>
    </>
  );
};

export default ReviewInfoContainer;
