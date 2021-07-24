import { ButtonHTMLAttributes } from "react";

import styled from "styled-components";
import { Shape } from "types/styled/common";
import { ButtonThemeColor } from "types/styled/components";

import { COLOR } from "utils/constants/color";

interface InnerProps {
  themeColor?: ButtonThemeColor;
  shape?: keyof Shape;
  border?: boolean;
  hover?: boolean;
  active?: boolean;
}

const Inner = styled.button<InnerProps>`
  color: ${({ theme, themeColor = "primary" }) => theme.components.button[themeColor].color};
  background-color: ${({ theme, themeColor = "primary", active = false }) =>
    active ? theme.components.button[themeColor].activeBg : theme.components.button[themeColor].bg};
  border-radius: ${({ theme, shape = "rounded" }) => theme.common.shape[shape]};
  transition: background-color 0.1s ease-in-out;
  padding: 0.375rem 0.625rem;
  ${({ border }) => border && `border: 1px solid ${COLOR.GRAY_400};`}

  ${({ hover = true }) =>
    hover &&
    `:hover {
        box-shadow: 0 0 5px ${COLOR.GRAY_500};
      }`}

  :active {
    background-color: ${({ theme, themeColor = "primary" }) => theme.components.button[themeColor].activeBg};
  }
`;

export type Props = ButtonHTMLAttributes<HTMLButtonElement> & InnerProps;

const Button = (props: Props) => <Inner {...props} />;

export default Button;
