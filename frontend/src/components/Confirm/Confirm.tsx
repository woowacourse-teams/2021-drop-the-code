import styled from "styled-components";

import useModalContext from "hooks/useModalContext";
import { COLOR } from "utils/constants/color";

const Inner = styled.div`
  width: 18.75rem;
  height: 11.25rem;
  flex-wrap: wrap;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  background-color: ${COLOR.WHITE};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
`;

const Title = styled.section`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-basis: 100%;
  height: 60%;
  border-bottom: 1px solid ${COLOR.GRAY_100};
`;

const ButtonControls = styled.section`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 40%;
  font-weight: bold;

  > button {
    width: 50%;
    height: 100%;
  }

  > button:hover {
    background-color: ${COLOR.GRAY_100};
  }

  > button:last-child {
    border-left: 1px solid ${COLOR.GRAY_100};
  }
`;

export interface Props {
  title: string;
  onConfirm?: () => void;
  onReject?: () => void;
}

const Confirm = ({ title, onConfirm, onReject }: Props) => {
  const { close } = useModalContext();

  const confirm = () => {
    onConfirm?.();
    close();
  };

  const reject = () => {
    onReject?.();
    close();
  };

  return (
    <Inner>
      <Title>
        <p>{title}</p>
      </Title>
      <ButtonControls>
        <button type="button" onClick={reject}>
          <p>취소</p>
        </button>
        <button type="button" onClick={confirm} data-testid="confirm-button">
          <p>확인</p>
        </button>
      </ButtonControls>
    </Inner>
  );
};

export default Confirm;
