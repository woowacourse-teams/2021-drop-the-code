import { ButtonHTMLAttributes } from "react";

import styled from "styled-components";

import { COLOR_TYPE, SHAPE_TYPE } from "../../../types/styled";
import { COLOR } from "../../../utils/constants/color";

interface InnerProps {
  themeColor?: keyof COLOR_TYPE;
  shape?: keyof SHAPE_TYPE;
  outline?: boolean;
  hover?: boolean;
  active?: boolean;
}

const Inner = styled.button<InnerProps>`
  color: ${({ theme, themeColor = "primary" }) => theme.color[themeColor].text};
  background-color: ${({ theme, themeColor = "primary", active = false }) =>
    active ? theme.color[themeColor].active : theme.color[themeColor].normal};
  border-radius: ${({ theme, shape = "rounded" }) => theme.shape[shape]};
  transition: background-color 0.1s ease-in-out;
  padding: 0.625rem;
  ${({ outline }) => (outline ? `outline: 1px solid ${COLOR.WHITE}` : "")}

  ${({ hover = true }) =>
    hover &&
    `:hover {
        box-shadow: 0 0 5px ${COLOR.GRAY_500};
      }`}
  
  :active {
    background-color: ${({ theme, themeColor = "primary" }) => theme.color[themeColor].active};
  }
`;

export type Props = ButtonHTMLAttributes<HTMLButtonElement> & InnerProps;

const Button = (props: Props) => <Inner {...props} />;

export default Button;
