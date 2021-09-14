import { useEffect, useRef } from "react";
import { useQueryClient } from "react-query";

import { Stomp, CompatClient } from "@stomp/stompjs";
import styled from "styled-components";

import FormProvider from "components/FormProvider/FormProvider";
import InputField from "components/FormProvider/InputField";
import Loading from "components/Loading/Loading";
import Button from "components/shared/Button/Button";
import useAuthContext from "hooks/useAuthContext";
import useChattingConnect from "hooks/useChattingConnect";
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

interface Message {
  roomId: number;
  senderId: number;
  receiverId: number | null;
  content: string;
}

interface Props {
  teacherId: number | null;
}

const CurrentChattingForm = ({ teacherId }: Props) => {
  const { user } = useAuthContext();
  const queryClient = useQueryClient();

  // roomId 만드는 연결
  const { data } = useChattingConnect({ studentId: user?.id, teacherId });

  if (!user || !data) return <Loading />;

  const stompClient = useRef<CompatClient | null>(null);

  useEffect(() => {
    const socket = new WebSocket(`ws://${process.env.SOCKET_URL}/ws-connection`);
    stompClient.current = Stomp.over(socket);

    connect();

    return () => disconnect();
  }, []);

  const connect = () => {
    if (!stompClient.current) return;

    stompClient.current.configure({
      reconnectDelay: 5000,
    });

    stompClient.current.connect(
      {},
      () => {
        if (!stompClient.current) return;

        stompClient.current.subscribe(`/subscribe/rooms/${data?.roomId}`, () => {
          console.log("subscribed");
        });
        console.log("connected");
      },
      () => console.log("connection error")
    );
  };

  const sendMessage = ({ roomId, senderId, receiverId, content }: Message) => {
    if (!stompClient.current) return;

    stompClient.current.send(
      `/publish/rooms/${roomId}`,
      {},
      JSON.stringify({
        message: content,
        senderId: senderId,
        receiverId: receiverId,
      })
    );

    queryClient.invalidateQueries(QUERY_KEY.GET_CHATTING_LIST);
    queryClient.invalidateQueries(QUERY_KEY.GET_SINGLE_CHATTING);
  };

  const disconnect = () => {
    if (!stompClient.current) return;

    stompClient.current.disconnect(() => {
      if (!stompClient.current) return;

      stompClient.current.deactivate();
      console.log("disconnected");
    });
  };

  return (
    <>
      {!data ? (
        <Loading />
      ) : (
        <FormProvider
          submit={({ content }) => {
            sendMessage({
              roomId: data.roomId,
              senderId: user.id,
              receiverId: teacherId,
              content,
            });
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
      )}
    </>
  );
};

export default CurrentChattingForm;
