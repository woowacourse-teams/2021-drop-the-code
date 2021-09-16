import { Dispatch, SetStateAction, useEffect } from "react";

import styled, { css } from "styled-components";

import Avatar from "components/shared/Avatar/Avatar";
import { Flex, FlexAlignCenter, FlexCenter } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useChattingList from "hooks/useChattingList";
import useStompContext from "hooks/useStompContext";
import { COLOR } from "utils/constants/color";
import { formatTimeToPassedTime, removeMillisecond } from "utils/formatter";

const Inner = styled(Flex)`
  max-width: 30rem;
  width: 30rem;
  height: 93vh;
  padding: 0.625rem 0;
  border: 1px solid ${COLOR.GRAY_200};
  overflow-y: auto;
`;

const Content = styled.div`
  width: 9.375rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: ${COLOR.GRAY_500};
`;

const Time = styled.div`
  width: 3.625rem;
  font-size: 0.875rem;
  text-align: end;
`;

const ChattingItem = styled.li<{ active?: boolean; hover?: boolean }>`
  display: flex;

  :hover {
    background-color: ${({ hover = true }) => (hover ? COLOR.GRAY_100 : null)};
  }

  background-color: ${({ active = true }) => css`
    ${active ? COLOR.GRAY_100 : COLOR.WHITE}
  `};
`;

const SingleItemInner = styled(FlexCenter)`
  width: 100%;
  margin: 0.9375rem 1.25rem;
`;

interface Props {
  selectedRoomId: number | null;
  setSelectedRoomId: Dispatch<SetStateAction<number | null>>;
  setSelectedTeacherId: Dispatch<SetStateAction<number | null>>;
}

const ChattingList = ({ selectedRoomId, setSelectedRoomId, setSelectedTeacherId }: Props) => {
  const { disconnect } = useStompContext();
  const { user } = useAuthContext();
  const { chattingList } = useChattingList(user?.id);

  chattingList.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

  useEffect(() => {
    setSelectedRoomId(null);
    setSelectedTeacherId(null);
  }, []);

  return (
    <Inner>
      <ul>
        {chattingList.length === 0 ? (
          <FlexAlignCenter css={{ justifyContent: "center", width: "20rem", height: "100%", color: COLOR.GRAY_500 }}>
            채팅 목록이 없습니다.
          </FlexAlignCenter>
        ) : (
          chattingList.map((item) => (
            <ChattingItem
              key={item.id}
              active={item.roomId === selectedRoomId}
              onClick={() => {
                disconnect();

                setSelectedRoomId(item.roomId);
                setSelectedTeacherId(item.id);
              }}
            >
              <SingleItemInner>
                <Avatar imageUrl={item.imageUrl} width="2.5rem" css={{ marginRight: "0.625rem" }} />
                <Content css={{ marginRight: "0.625rem" }}>{item.latestMessage}</Content>
                <Time>{formatTimeToPassedTime(new Date(removeMillisecond(item.createdAt)))}</Time>
              </SingleItemInner>
            </ChattingItem>
          ))
        )}
      </ul>
    </Inner>
  );
};

export default ChattingList;
