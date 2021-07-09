import { ReactNode } from "react";

import styled from "styled-components";

import { LAYOUT } from "../../utils/constants/size";
import { COLOR_TYPE } from "../../utils/constants/theme";
import { FlexSpaceBetween } from "../shared/Flexbox/Flexbox";

interface InnerProps {
  themeColor?: COLOR_TYPE;
}

const Inner = styled.header<InnerProps>`
  background-color: ${({ theme, themeColor = "primary" }) => theme.color[themeColor].normal};
  width: 100%;
  height: 4rem;
  display: flex;
  justify-content: center;
`;

export interface Props extends InnerProps {
  title?: ReactNode;
  children?: ReactNode;
}

const Header = ({ title, children }: Props) => {
  return (
    <Inner>
      <FlexSpaceBetween
        css={{ maxWidth: LAYOUT.LG, width: "100%", height: "100%", alignItems: "center", padding: "0 30px" }}
      >
        {title}
        <div>{children}</div>
      </FlexSpaceBetween>
    </Inner>
  );
};

export default Header;
