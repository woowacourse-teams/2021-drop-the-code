import { ReactNode, useState } from "react";

import styled from "styled-components";

import { COLOR } from "../../utils/constants/color";
import { Z_INDEX } from "../../utils/constants/size";
import Button, { Props as ButtonProps } from "../shared/Button/Button";

const Inner = styled.div`
  display: flex;
  position: relative;
  width: 100%;
`;

const Dimmed = styled.div`
  position: fixed;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
`;

const Menu = styled.div`
  position: absolute;
  top: 50px;
  background: ${COLOR.WHITE};
  border-radius: 0.75rem;
  box-shadow: rgb(0 0 0 / 15%) 0 0.625rem 1.875rem;
  overflow: auto;
  right: 0;
  padding: 1.875rem;
  z-index: ${Z_INDEX.FLOATING_MENU};
`;

export interface Props extends Omit<Omit<ButtonProps, "active">, "onClick"> {
  contents?: (props: () => void) => ReactNode;
}

const FloatingMenuButton = ({ contents, children, ...props }: Props) => {
  const [isOpen, setOpen] = useState(false);

  const close = () => {
    setOpen(false);
  };

  const dimmedClick: React.MouseEventHandler = ({ target, currentTarget }) => {
    console.log(target, currentTarget);
    if (target !== currentTarget) return;

    close();
  };

  return (
    <Inner>
      <Button
        onClick={() => {
          setOpen(!isOpen);
        }}
        {...props}
      >
        {children}
      </Button>
      {isOpen && (
        <>
          <Dimmed onClick={dimmedClick} />
          <Menu>{contents && contents(close)}</Menu>
        </>
      )}
    </Inner>
  );
};

export default FloatingMenuButton;
