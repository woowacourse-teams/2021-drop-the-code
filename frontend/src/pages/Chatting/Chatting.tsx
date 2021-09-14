import { Suspense, useState } from "react";
import { useLocation } from "react-router-dom";

import ChattingList from "components/Chatting/ChattingList";
import CurrentChatting from "components/Chatting/CurrentChatting";
import Loading from "components/Loading/Loading";
import { Flex } from "components/shared/Flexbox/Flexbox";

const Chatting = () => {
  const [selectedRoomId, setSelectedRoomId] = useState<number | null>(null);
  const [selectedTeacherId, setSelectedTeacherId] = useState<number | null>(null);

  const location = useLocation<{ teacherId: number; teacherImage: string; teacherName: string }>();

  return (
    <Flex>
      <Suspense fallback={<Loading />}>
        <ChattingList
          selectedRoomId={selectedRoomId}
          setSelectedRoomId={setSelectedRoomId}
          setSelectedTeacherId={setSelectedTeacherId}
        />
      </Suspense>
      <Suspense fallback={<Loading />}>
        <CurrentChatting
          selectedRoomId={selectedRoomId}
          selectedTeacherId={selectedTeacherId}
          setSelectedRoomId={setSelectedRoomId}
          setSelectedTeacherId={setSelectedTeacherId}
          teacherId={location.state ? location.state.teacherId : -1}
          teacherImage={location.state && location.state.teacherImage}
          teacherName={location.state && location.state.teacherName}
        />
      </Suspense>
    </Flex>
  );
};

export default Chatting;
