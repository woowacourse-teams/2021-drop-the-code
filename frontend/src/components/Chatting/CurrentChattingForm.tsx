import { useEffect } from "react";

import styled from "styled-components";

import FormProvider from "components/FormProvider/FormProvider";
import InputField from "components/FormProvider/InputField";
import Loading from "components/Loading/Loading";
import Button from "components/shared/Button/Button";
import useAuthContext from "hooks/useAuthContext";
import useChattingConnect from "hooks/useChattingConnect";
import useStompContext from "hooks/useStompContext";
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
}

const CurrentChattingForm = ({ teacherId }: Props) => {
  const { user } = useAuthContext();
  const { sendMessage, connect } = useStompContext();

  // roomId 만드는 연결
  const { data } = useChattingConnect({ studentId: user?.id, teacherId });

  useEffect(() => {
    if (!data) return;

    connect(data.roomId);
  }, [data]);

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
