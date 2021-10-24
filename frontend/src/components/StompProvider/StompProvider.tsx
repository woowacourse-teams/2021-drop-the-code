import { ReactNode, useRef } from "react";
import { useQueryClient } from "react-query";

import { CompatClient, Stomp } from "@stomp/stompjs";

import { StompContext } from "hooks/useStompContext";
import { QUERY_KEY } from "utils/constants/key";

export interface Message {
  roomId: number;
  senderId: number;
  receiverId: number | null;
  content: string;
}

interface Props {
  children: ReactNode;
}

const StompProvider = ({ children }: Props) => {
  const stompClient = useRef<CompatClient | null>(null);

  const queryClient = useQueryClient();

  const disconnect = () => {
    if (!stompClient.current) return;

    stompClient.current.disconnect();
  };

  const connect = (roomId: number) => {
    stompClient.current = Stomp.over(() => {
      return new WebSocket(process.env.SOCKET_URL as string);
    });

    stompClient.current.configure({
      reconnectDelay: 5000,
    });

    stompClient.current.connect(
      {},
      () => {
        if (!stompClient.current) return;

        stompClient.current.subscribe(`/subscribe/rooms/${roomId}`, () => {
          console.log("subscribed");

          queryClient.invalidateQueries(QUERY_KEY.GET_CHATTING_LIST);
          queryClient.invalidateQueries(QUERY_KEY.GET_SINGLE_CHATTING);
        });

        console.log("connected");
      },
      () => console.log("connection error")
    );
  };

  const sendMessage = ({ roomId, senderId, receiverId, content }: Message) => {
    if (!stompClient.current) return;

    if (stompClient.current.state !== 0) return;

    stompClient.current.send(
      `/publish/rooms/${roomId}`,
      {},
      JSON.stringify({
        message: content,
        senderId: senderId,
        receiverId: receiverId,
      })
    );
  };

  return (
    <StompContext.Provider value={{ stompClient, connect, disconnect, sendMessage }}>{children}</StompContext.Provider>
  );
};

export default StompProvider;
