import { Dispatch, SetStateAction, Suspense, useEffect, useRef } from "react";

import { nanoid } from "nanoid";
import styled from "styled-components";

import LeftSingleMessage from "components/Chatting/LeftSingleMessage";
import RightSingleMessage from "components/Chatting/RightSingleMessage";
import Loading from "components/Loading/Loading";
import Avatar from "components/shared/Avatar/Avatar";
import { FlexAlignCenter } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useChattingList from "hooks/useChattingList";
import useSingleChatting from "hooks/useSingleChatting";
import { COLOR } from "utils/constants/color";
import { ALT } from "utils/constants/message";

import CurrentChattingForm from "./CurrentChattingForm";

const Inner = styled.div`
  border: 1px solid ${COLOR.GRAY_200};
  width: 100%;
`;

const Title = styled.div`
  font-size: 1rem;
  padding: 0.625rem 1.25rem;
  display: flex;
  align-items: center;
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
`;

const ContentWrapper = styled.div`
  height: 72vh;
  padding: 1.25rem;
  overflow-y: scroll;
`;

interface Props {
  selectedRoomId: number | null;
  selectedTeacherId: number | null;
  setSelectedRoomId: Dispatch<SetStateAction<number | null>>;
  setSelectedTeacherId: Dispatch<SetStateAction<number | null>>;
  teacherId?: number;
  teacherName?: string;
  teacherImage?: string;
}

const CurrentChatting = ({
  selectedRoomId,
  selectedTeacherId,
  setSelectedRoomId,
  setSelectedTeacherId,
  teacherId,
  teacherName,
  teacherImage,
}: Props) => {
  const { user } = useAuthContext();

  const { chattingList } = useChattingList(user?.id);
  const { data } = useSingleChatting(selectedRoomId);

  const chattingContainer = useRef<HTMLDivElement | null>(null);

  data.sort((b, a) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

  const isAlreadyMet = chattingList.filter((chatting) => chatting.id === teacherId);

  useEffect(() => {
    if (!isAlreadyMet) return;

    // ?????? ????????? ?????? ????????? ??????, ?????? ???????????? roomId ????????????
    if (isAlreadyMet.length > 0) {
      setSelectedRoomId(isAlreadyMet[0].roomId);
    }

    if (teacherId && teacherId > 0) setSelectedTeacherId(teacherId);
  }, []);

  useEffect(() => {
    chattingContainer.current?.scrollTo(0, chattingContainer.current.scrollHeight);
  }, [data]);

  if (!user) return <Loading />;

  /* ??????????????? ????????? ???????????? ????????? ????????????, ?????? ?????? ????????? ?????? ?????? */
  if (data.length > 0 && selectedRoomId)
    return (
      <Inner>
        <Title>
          <Avatar
            imageUrl={data[0].senderId === user.id ? data[0].receiverImageUrl : data[0].senderImageUrl}
            width="3rem"
            css={{ marginRight: "0.625rem" }}
            alt={`${data[0].senderId === user.id ? data[0].receiverImageUrl : data[0].senderImageUrl} ${
              ALT.PROFILE_AVATAR
            }`}
          />
          <div css={{ marginRight: "0.625rem" }}>
            {data[0].senderId === user.id ? data[0].receiverName : data[0].senderName}??? ?????????
          </div>
        </Title>
        <ContentWrapper ref={chattingContainer}>
          {user &&
            data.map((chatting) =>
              chatting.senderId !== user.id ? (
                <LeftSingleMessage key={nanoid()} message={chatting.message} imageUrl={chatting.senderImageUrl} />
              ) : (
                <RightSingleMessage key={nanoid()} message={chatting.message} imageUrl={chatting.senderImageUrl} />
              )
            )}
        </ContentWrapper>
        <Suspense fallback={<Loading />}>
          <CurrentChattingForm
            chattingContainer={chattingContainer}
            teacherId={selectedTeacherId}
            setSelectedRoomId={setSelectedRoomId}
          />
        </Suspense>
      </Inner>
    );

  /* ??????????????? ????????? ???????????? ????????? ????????????, ?????? ?????? ????????? ?????? ?????? ????????? ?????? */
  if (teacherId && teacherImage && teacherName && teacherId > 0)
    return (
      <Inner>
        <Title>
          <Avatar
            imageUrl={teacherImage}
            width="3rem"
            css={{ marginRight: "0.625rem" }}
            alt={`${teacherId} ${ALT.PROFILE_AVATAR}`}
          />
          <div css={{ marginRight: "0.625rem" }}>{teacherName}??? ?????????</div>
        </Title>
        <ContentWrapper />
        <Suspense fallback={<Loading />}>
          <CurrentChattingForm
            chattingContainer={chattingContainer}
            teacherId={selectedTeacherId}
            setSelectedRoomId={setSelectedRoomId}
          />
        </Suspense>
      </Inner>
    );

  /* header?????? ???????????? ?????? ????????? ?????? ???????????? ????????? ?????? ?????? ?????? */
  return (
    <Inner>
      <FlexAlignCenter css={{ justifyContent: "center", height: "100%", color: COLOR.GRAY_500 }}>
        ???????????? ??????????????????...
      </FlexAlignCenter>
    </Inner>
  );
};

export default CurrentChatting;
