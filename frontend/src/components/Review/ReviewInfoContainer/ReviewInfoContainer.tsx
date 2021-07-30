import { useMutation, useQuery } from "react-query";

import styled from "styled-components";
import { Progress } from "types/review";

import { getReview, patchReviewProgress } from "apis/review";
import Notification from "assets/notification.png";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import ContentBox from "components/shared/ContentBox/ContentBox";
import { FlexAlignCenter, Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { COLOR } from "utils/constants/color";
import { ALT, SUCCESS_MESSAGE } from "utils/constants/message";

const Title = styled.p`
  font-weight: 900;
`;

const PrUrl = styled.a`
  margin-left: auto;
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
  const { revalidate } = useRevalidate();

  const { data } = useQuery("getReview", async () => {
    const response = await getReview(reviewId);

    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  const mutation = useMutation(({ id, progress }: { id: number; progress: Progress }) => {
    return revalidate(async () => {
      const response = await patchReviewProgress(id, progress);

      if (!response.isSuccess) {
        toast(response.error.message);
      } else {
        toast(SUCCESS_MESSAGE.API.REVIEW.PATCH_PROGRESS);
      }

      return response;
    });
  });

  if (!data) return <></>;

  const isFinished = data.progress === "FINISHED";
  const isAnonymous = !user || ![data.studentProfile.id, data.teacherProfile.id].includes(user.id);

  const [buttonText, disabled] =
    user?.role === "STUDENT"
      ? [user?.id === data.studentProfile.id && "리뷰 종료", data.progress !== "ON_GOING"]
      : [user?.id === data.teacherProfile.id && "리뷰 완료", data.progress !== "TEACHER_COMPLETED"];

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
            <p>{data.progress !== "FINISHED" ? "진행중" : "완료"}</p>
          </FlexAlignCenter>
        }
      >
        <Flex css={{ flexDirection: "column" }}>
          <p css={{ fontSize: "14px", marginBottom: "50px" }}>{data.content}</p>
          <PrUrl href={data.prUrl} target="_blank" rel="noopener">
            PR 링크 바로가기
          </PrUrl>
        </Flex>
      </ContentBox>
      <div css={{ width: "100%", marginBottom: "3.125rem" }}>
        <img src={Notification} alt={ALT.REVIEW_DETAIL_NOTIFICATION} />
      </div>
      <FlexCenter css={{ marginBottom: "6.25rem" }}>
        {!isFinished && !isAnonymous && (
          <Button
            disabled={disabled}
            onClick={() => {
              if (isFinished || isAnonymous || disabled) return;

              mutation.mutate({ id: reviewId, progress: data.progress });
            }}
          >
            {buttonText}
          </Button>
        )}
      </FlexCenter>
    </>
  );
};

export default ReviewInfoContainer;
