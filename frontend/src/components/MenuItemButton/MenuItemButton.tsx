import { ReactNode, useState } from "react";

import styled from "styled-components";

import Button, { Props as ButtonProps } from "components/shared/Button/Button";
import { COLOR } from "utils/constants/color";

const Inner = styled.div`
  display: inline-flex;
  position: relative;
  z-index: ${({ theme }) => theme.common.zIndex.menuItem};
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
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
  overflow: auto;
  right: 0;
  padding: 1.875rem;
  z-index: 1;
`;

export interface Props extends Omit<ButtonProps, "active" | "onClick"> {
  contents?: (props: () => void) => ReactNode;
}

const MenuItemButton = ({ contents, children, ...props }: Props) => {
  const [isOpen, setOpen] = useState(false);

  const close = () => {
    setOpen(false);
  };

  const dimmedClick: React.MouseEventHandler = ({ target, currentTarget }) => {
    if (target !== currentTarget) return;

    close();
  };

  return (
    <Inner>
      <Button
        {...props}
        onClick={() => {
          setOpen(!isOpen);
        }}
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

export default MenuItemButton;
