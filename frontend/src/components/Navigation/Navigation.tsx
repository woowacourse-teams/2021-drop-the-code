import { ReactNode } from "react";

import styled from "styled-components";

import { LAYOUT } from "../../utils/constants/size";
import { COLOR_TYPE } from "../../utils/constants/theme";
import { Flex, FlexSpaceBetween } from "../shared/Flexbox/Flexbox";

interface InnerProps {
  themeColor?: COLOR_TYPE;
}

const Inner = styled.header<InnerProps>`
  background-color: ${({ theme, themeColor = "primary" }) => theme.color[themeColor].normal};
  width: 100%;
  height: 4rem;
`;

export interface Props extends InnerProps {
  title?: ReactNode;
  children?: ReactNode;
}

const NavBar = styled.nav`
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;

  a:not(:first-child),
  button {
    margin-left: 1.875rem;
  }
`;

const Navigation = ({ title, children }: Props) => {
  return (
    <Inner>
      <NavBar>
        <FlexSpaceBetween
          css={{ maxWidth: LAYOUT.LG, width: "100%", height: "100%", alignItems: "center", padding: "0 30px" }}
        >
          {title}
          <Flex>{children}</Flex>
        </FlexSpaceBetween>
      </NavBar>
    </Inner>
  );
};

export default Navigation;
