import { ButtonHTMLAttributes } from "react";

import styled from "styled-components";

import { COLOR } from "../../../utils/constants/color";
import { COLOR_TYPE } from "../../../utils/constants/theme";

const SHAPE = {
  rounded: "0.25rem",
  pill: "6.25rem",
  circle: "100%",
};

interface InnerProps {
  themeColor?: COLOR_TYPE;
  shape?: keyof typeof SHAPE;
  outline?: boolean;
}

const Inner = styled.button<InnerProps>`
  color: ${({ theme, themeColor = "primary" }) => theme.color[themeColor].text};
  background-color: ${({ theme, themeColor = "primary" }) => theme.color[themeColor].normal};
  border-radius: ${({ shape = "rounded" }) => SHAPE[shape]};
  transition: background-color 0.1s ease-in-out;
  padding: 0.625rem;
  ${({ outline }) => (outline ? `outline: 1px solid ${COLOR.WHITE}` : "")}

  :hover {
    box-shadow: 0 0 5px ${COLOR.GRAY_500};
  }

  :active {
    background-color: ${({ theme, themeColor = "primary" }) => theme.color[themeColor].active};
  }
`;

export type Props = ButtonHTMLAttributes<HTMLButtonElement> & InnerProps;

const Button = (props: Props) => <Inner {...props} />;

export default Button;
