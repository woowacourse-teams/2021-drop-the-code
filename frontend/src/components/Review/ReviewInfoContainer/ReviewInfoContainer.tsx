import { useMutation, useQuery, useQueryClient } from "react-query";
import { Redirect } from "react-router-dom";

import styled from "styled-components";

import { getReview, patchReviewProgress } from "../../../apis/review";
import Notification from "../../../assets/notification.png";
import useAuthContext from "../../../hooks/useAuthContext";
import { COLOR } from "../../../utils/constants/color";
import { ALT } from "../../../utils/constants/message";
import { PATH } from "../../../utils/constants/path";
import Avatar from "../../shared/Avatar/Avatar";
import Button from "../../shared/Button/Button";
import ContentBox from "../../shared/ContentBox/ContentBox";
import { FlexAlignCenter, Flex, FlexCenter } from "../../shared/Flexbox/Flexbox";

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

/*


data.progress === 'ON_GOING'
data.progress === 'TEACHER_COMPLETED'
data.progress === 'FINISHED'

user.id === data.studentProfile.id + data.progress ==='ON_GOING' 리뷰 종료 / disabled
user.id === data.teacherProfile.id + data.progress === 'TEACHER_COMPLETED' 이면  리뷰 완료 / disabled
'FINISHED' 이면 disabled
익명의 참석자이면 button 생성 X

[buttonText, disabled] = user.role === 'student' ? [user.id === data.studentProfile.id && 리뷰 종료, data.progress !== 'ON_GOING'] : = [user.id === data.teacherProfile.id && 리뷰 완료, data.progress !== 'TEACHER_COMPLETED']
[buttonText, disabled] = [user.id === data.teacherProfile.id && 리뷰 완료, data.progress !== 'TEACHER_COMPLETED']
buttonText = user.id === data.teacherProfile.id && 리뷰 완료

const isAnonymous = ![data.studentProfile.id, data.teacherProfile.id].includes(user.id)



"ON_GOING" | "TEACHER_COMPLETED" | "FINISHED";
*/
