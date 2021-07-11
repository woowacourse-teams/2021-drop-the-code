import { ReactNode } from "react";

import styled from "styled-components";

import { COLOR } from "../../utils/constants/color";
import { LAYOUT } from "../../utils/constants/size";
import { Flex, FlexSpaceBetween } from "../shared/Flexbox/Flexbox";

const Inner = styled.header`
  background-color: ${({ theme }) => theme.color.white.normal};
  width: 100%;
  height: 4rem;
  box-shadow: 0 0 30px rgb(0 0 0 / 0.2);
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
    color: ${({ theme }) => theme.color.white.text};
    border-radius: ${({ theme }) => theme.shape.rounded};
    transition: background-color 0.1s ease-in-out;
    padding: 0.625rem;

    margin-right: 0.625rem;

    :hover {
      box-shadow: 0 0 5px ${COLOR.GRAY_500};
    }

    :active {
      background-color: ${({ theme }) => theme.color.white.active};
    }
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
        <FlexSpaceBetween
          css={{ maxWidth: LAYOUT.LG, width: "100%", height: "100%", alignItems: "center", padding: "0 30px" }}
        >
          {children}
          <RightChildren>{rightChildren}</RightChildren>
        </FlexSpaceBetween>
      </NavBar>
    </Inner>
  );
};

export default Navigation;
