import { createContext, MutableRefObject, useContext } from "react";

import { CompatClient } from "@stomp/stompjs";

import { Message } from "components/StompProvider/StompProvider";

interface Stomp {
  stompClient: MutableRefObject<CompatClient | null>;
  connect: (roomId: number) => void;
  disconnect: () => void;
  sendMessage: (message: Message) => void;
}

export const StompContext = createContext<Stomp | null>(null);

const useStompContext = () => {
  const context = useContext(StompContext);

  if (!context) throw Error("StompContext가 존재하지 않습니다");

  return context;
};

export default useStompContext;
