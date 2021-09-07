import styled from "styled-components";

import { chattingList } from "__mock__/data/chatting";
import Avatar from "components/shared/Avatar/Avatar";
import { Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";
// import useChattingList from "hooks/useChattingList";
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

const ChattingItem = styled.li`
  display: flex;

  :hover {
    background-color: ${COLOR.GRAY_100};
  }

  :active {
    background-color: ${COLOR.GRAY_200};
  }
`;

const SingleItemInner = styled(FlexCenter)`
  width: 100%;
  margin: 0.9375rem 1.25rem;
`;

const ChattingList = () => {
  // const { data, selectedChatting, setSelectedChatting } = useChattingList({ id });

  chattingList.sort((a, b) => removeMillisecond(b.createdAt).getTime() - removeMillisecond(a.createdAt).getTime());

  return (
    <Inner>
      <ul>
        {chattingList &&
          chattingList.map((item) => (
            <ChattingItem key={item.id}>
              <SingleItemInner>
                <Avatar imageUrl={item.imageUrl} width="2.5rem" css={{ marginRight: "0.625rem" }} />
                <Content css={{ marginRight: "0.625rem" }}>{item.latestMessage}</Content>
                <Time>{formatTimeToPassedTime(removeMillisecond(item.createdAt))}</Time>
              </SingleItemInner>
            </ChattingItem>
          ))}
      </ul>
    </Inner>
  );
};

export default ChattingList;
