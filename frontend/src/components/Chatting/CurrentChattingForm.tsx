import { Dispatch, SetStateAction, MutableRefObject, useEffect } from "react";

import styled from "styled-components";

import { queryClient } from "__mock__/utils/testUtils";
import FormProvider from "components/FormProvider/FormProvider";
import InputField from "components/FormProvider/InputField";
import Loading from "components/Loading/Loading";
import Button from "components/shared/Button/Button";
import useAuthContext from "hooks/useAuthContext";
import useChattingConnect from "hooks/useChattingConnect";
import useStompContext from "hooks/useStompContext";
import { QUERY_KEY } from "utils/constants/key";
import { PLACE_HOLDER } from "utils/constants/message";

const InputWrapper = styled.div`
  position: relative;
  width: 80%;
  height: 3.125rem;
  left: 50%;
  transform: translateX(-50%);
`;

const ChattingInput = styled(InputField)`
  position: absolute;
  bottom: 0;
  left: 0.625rem;
  width: 85%;
  height: 100%;
  padding: 1.25rem;
`;

const SubmitButton = styled(Button)`
  position: absolute;
  bottom: 0.625rem;
  right: 0.625rem;
`;

interface Props {
  teacherId: number | null;
  chattingContainer: MutableRefObject<HTMLDivElement | null>;
  setSelectedRoomId: Dispatch<SetStateAction<number | null>>;
}

const CurrentChattingForm = ({ teacherId, chattingContainer, setSelectedRoomId }: Props) => {
  const { user } = useAuthContext();
  const { sendMessage, connect } = useStompContext();

  // roomId 만드는 연결
  const { data } = useChattingConnect({ studentId: user?.id, teacherId });

  useEffect(() => {
    if (!data) return;

    connect(data.roomId);
  }, [data]);

  useEffect(() => {
    chattingContainer.current?.scrollTo(0, chattingContainer.current.scrollHeight);
  }, [teacherId]);

  if (!user || !data) return <Loading />;

  return (
    <FormProvider
      submit={({ content }) => {
        sendMessage({
          roomId: data.roomId,
          senderId: user.id,
          receiverId: teacherId,
          content,
        });

        setSelectedRoomId(data.roomId);
        queryClient.invalidateQueries(QUERY_KEY.GET_CHATTING_LIST);
        queryClient.invalidateQueries(QUERY_KEY.GET_SINGLE_CHATTING);
      }}
      css={{ paddingTop: "0.9375rem" }}
    >
      <InputWrapper>
        <ChattingInput name="content" placeholder={PLACE_HOLDER.CHATTING.CONTENT} required />
        <SubmitButton themeColor="secondary" hover={false} active={false}>
          보내기
        </SubmitButton>
      </InputWrapper>
    </FormProvider>
  );
};

export default CurrentChattingForm;
