import { useMutation, useQuery, useQueryClient } from "react-query";
import { Redirect } from "react-router-dom";

import styled from "styled-components";

import { getReview, patchReviewProgress } from "apis/review";
import Notification from "assets/notification.png";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import ContentBox from "components/shared/ContentBox/ContentBox";
import { FlexAlignCenter, Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import { COLOR } from "utils/constants/color";
import { ALT } from "utils/constants/message";
import { PATH } from "utils/constants/path";

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

  const queryClinet = useQueryClient();

  const { data } = useQuery("getReview", async () => {
    const response = await getReview(reviewId);

    if (!response.isSuccess) {
      // TODO: 스낵바에 전달
      // response.error.message;
      return;
    }

    return response.data;
  });

  const mutation = useMutation(patchReviewProgress, {
    onSuccess: () => {
      queryClinet.invalidateQueries("getReview");
      /**/
    },
    onError: () => {
      /**/
    },
  });

  /*TODO: 리뷰 진행 상태에 따른 분기

    진행버튼의 Text와 disabled 상태의 분류
  
    리뷰가 완료됨, 리뷰와 관련 없는 사람의 방문, 리뷰 진행의 권한이 없는 경우 (Role, 리뷰 Progress 비교)
  */

  // const isFinished = data.progress === "FINISHED"
  // const isAnonymous = ![data.studentProfile.id, data.teacherProfile.id].includes(user.id);

  // const [buttonText, disabled] =
  //   user.role === "student"
  //     ? [user?.id === data?.studentProfile.id && "리뷰 종료", data?.progress !== "ON_GOING"]
  //     : [user?.id === data?.teacherProfile.id && "리뷰 완료", data?.progress !== "TEACHER_COMPLETED"];

  return (
    <>
      {data && (
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
                    {/* <p>{data.createAt.join(".")}</p> */}
                    <p css={{ width: "5.625rem" }}>2021.06.07</p>
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
            {/* {!isFinished && !isAnonymous && <Button disabled={disabled}>{buttonText}</Button>} */}
            <Button
              onClick={() => {
                // if (isFinished || isAnonymous || disabled) return;

                mutation.mutate(reviewId);
              }}
            >
              리뷰완료
            </Button>
          </FlexCenter>
        </>
      )}
    </>
  );
};

export default ReviewInfoContainer;
