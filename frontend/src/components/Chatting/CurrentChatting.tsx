import styled from "styled-components";

import { singleChatting } from "__mock__/data/chatting";
import LeftSingleMessage from "components/Chatting/LeftSingleMessage";
import RightSingleMessage from "components/Chatting/RightSingleMessage";
import FormProvider from "components/FormProvider/FormProvider";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import { COLOR } from "utils/constants/color";

// import useAuthContext from "hooks/useAuthContext";
// import useChattingList from "hooks/useChattingList";

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
  height: 78vh;
  padding: 1.25rem;
  overflow-y: scroll;
`;

const InputWrapper = styled.div`
  position: relative;
  background-color: white;
  width: 80%;
  height: 3.125rem;
  border: 1px solid ${COLOR.GRAY_200};
  left: 50%;
  transform: translateX(-50%);
  border-radius: 1.25rem;
`;

const ChattingInput = styled.input`
  position: absolute;
  bottom: 0;
  left: 0.625rem;
  width: 85%;
  height: 100%;
  padding: 1.25rem;
  outline: none;
  border: none;
`;

const SubmitButton = styled(Button)`
  position: absolute;
  bottom: 0.625rem;
  right: 0.625rem;
`;

const CurrentChatting = () => {
  // const { user } = useAuthContext();
  // const { data, selectedChatting, setSelectedChatting } = useChattingList({ id });

  singleChatting.sort(
    (a, b) => new Date(a.createdAt.slice(0, -4)).getTime() - new Date(b.createdAt.slice(0, -4)).getTime()
  );
  const myId = 1;

  return (
    <Inner>
      {singleChatting && (
        <Title>
          <Avatar
            imageUrl={
              singleChatting[0].senderId === myId
                ? singleChatting[0].receiverImageUrl
                : singleChatting[0].senderImageUrl
            }
            width="3rem"
            css={{ marginRight: "0.625rem" }}
          />
          <div css={{ marginRight: "0.625rem" }}>
            {singleChatting[0].senderId === myId ? singleChatting[0].receiverName : singleChatting[0].senderName}와
            채팅중
          </div>
        </Title>
      )}
      <ContentWrapper>
        {singleChatting &&
          singleChatting.map((chatting) =>
            chatting.senderId !== myId ? (
              <LeftSingleMessage
                key={chatting.createdAt}
                message={chatting.message}
                imageUrl={chatting.senderImageUrl}
              />
            ) : (
              <RightSingleMessage
                key={chatting.createdAt}
                message={chatting.message}
                imageUrl={chatting.senderImageUrl}
              />
            )
          )}
      </ContentWrapper>
      <FormProvider submit={() => console.log("submit!")} css={{ paddingTop: "0.9375rem" }}>
        <InputWrapper>
          <ChattingInput name="content" placeholder="메시지를 입력하세요." required />
          <SubmitButton themeColor="secondary" hover={false} active={false}>
            보내기
          </SubmitButton>
        </InputWrapper>
      </FormProvider>
    </Inner>
  );
};

export default CurrentChatting;
