import { ReactNode } from "react";

import styled from "styled-components";

import { Flex, FlexSpaceBetween } from "components/shared/Flexbox/Flexbox";

const Inner = styled.header`
  background-color: ${({ theme }) => theme.components.navigation.bg};
  height: 4rem;
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
  position: fixed;
  top: 0;
  width: 100%;
  z-index: ${({ theme }) => theme.common.zIndex.header};
`;

export interface Props {
  leftChildren?: ReactNode;
  children?: ReactNode;
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

const NavLinksWrapper = styled(FlexSpaceBetween)`
  max-width: ${({ theme }) => theme.common.layout.lg};
  width: 100%;
  height: 100%;
  align-items: center;
`;

const Navigation = ({ leftChildren, children }: Props) => {
  return (
    <Inner>
      <NavBar>
        <NavLinksWrapper>
          {leftChildren}
          <RightChildren>{children}</RightChildren>
        </NavLinksWrapper>
      </NavBar>
    </Inner>
  );
};

export default Navigation;
