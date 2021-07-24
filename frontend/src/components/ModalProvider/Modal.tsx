import React, { ReactNode } from "react";

import styled from "styled-components";

import ModalPortal from "components/ModalProvider/ModalPortal";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import useModalContext from "hooks/useModalContext";

export interface Props {
  children: ReactNode;
}

const ModalBlock = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Dimmed = styled.div`
  position: fixed;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  top: 0;
  left: 0;
`;

const Contents = styled(FlexCenter)`
  padding: 20px;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  background-color: white;
  z-index: ${({ theme }) => theme.common.zIndex.modal};
`;

const Modal = ({ children }: Props) => {
  const { close } = useModalContext();

  const dimmedClick: React.MouseEventHandler<HTMLDivElement> = ({ target, currentTarget }) => {
    if (target !== currentTarget) return;

    close();
  };

  return ModalPortal(
    <ModalBlock>
      <Dimmed onClick={dimmedClick} />
      <Contents>{children}</Contents>
    </ModalBlock>
  );
};

export default Modal;
