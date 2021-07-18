import { ReactNode } from "react";

import styled from "styled-components";

import { LAYOUT } from "../../utils/constants/size";
import { Flex, FlexSpaceBetween } from "../shared/Flexbox/Flexbox";

const Inner = styled.header`
  background-color: ${({ theme }) => theme.components.navigation.bg};
  height: 6rem;
  box-shadow: 0 0 30px rgb(0 0 0 / 0.2);
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1200;
`;

export interface Props {
  children?: ReactNode;
  rightChildren?: ReactNode;
}

const NavBar = styled.nav`
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

const RightChildren = styled(Flex)`
  a {
    align-items: center;
    color: ${({ theme }) => theme.components.navigation.color};
    border-radius: ${({ theme }) => theme.common.shape.rounded};
    transition: background-color 0.1s ease-in-out;
    padding: 0.625rem;

    margin-right: 0.625rem;
  }

  a,
  button {
    font-weight: 500;
  }
`;

const Navigation = ({ rightChildren, children }: Props) => {
  return (
    <Inner>
      <NavBar>
        <FlexSpaceBetween css={{ maxWidth: LAYOUT.LG, width: "100%", height: "100%", alignItems: "center" }}>
          {children}
          <RightChildren>{rightChildren}</RightChildren>
        </FlexSpaceBetween>
      </NavBar>
    </Inner>
  );
};

export default Navigation;
