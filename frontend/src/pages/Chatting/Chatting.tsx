import { Suspense } from "react";

import ChattingList from "components/Chatting/ChattingList";
import CurrentChatting from "components/Chatting/CurrentChatting";
import Loading from "components/Loading/Loading";
import { Flex } from "components/shared/Flexbox/Flexbox";

const Chatting = () => {
  return (
    <Flex>
      <Suspense fallback={<Loading />}>
        <ChattingList />
      </Suspense>
      <Suspense fallback={<Loading />}>
        <CurrentChatting />
      </Suspense>
    </Flex>
  );
};

export default Chatting;
