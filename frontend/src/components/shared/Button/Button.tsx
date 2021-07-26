import { ButtonHTMLAttributes } from "react";

import styled, { css } from "styled-components";
import { Shape } from "types/styled/common";
import { ButtonThemeColor } from "types/styled/components";

import { COLOR } from "utils/constants/color";

interface InnerProps {
  themeColor?: ButtonThemeColor;
  shape?: keyof Shape;
  border?: boolean;
  hover?: boolean;
  active?: boolean;
  disabled?: boolean;
}

const Inner = styled.button<InnerProps>`
  transition: background-color 0.1s ease-in-out;
  padding: 0.375rem 0.625rem;

  color: ${({ theme, themeColor = "primary" }) => css`
    ${theme.components.button[themeColor].color};
  `};

  background-color: ${({ theme, themeColor = "primary", active = false }) => css`
    ${active ? theme.components.button[themeColor].activeBg : theme.components.button[themeColor].bg};
  `};

  border-radius: ${({ theme, shape = "rounded" }) => css`
    ${theme.common.shape[shape]};
  `};

  border: ${({ border }) =>
    border &&
    css`
      1px solid ${COLOR.GRAY_400};
    `};

  :hover {
    box-shadow: ${({ hover = true }) =>
      hover &&
      css`
        0.125rem 0.125rem 0.25rem rgb(0 0 0 / 30%);
      `};
  }

  :active {
    background-color: ${({ theme, themeColor = "primary" }) => css`
      ${theme.components.button[themeColor].activeBg};
    `};
  }

  :disabled {
    background-color: ${COLOR.GRAY_300};

    :hover {
      box-shadow: none;
    }

    cursor: auto;
  }
`;

export type Props = ButtonHTMLAttributes<HTMLButtonElement> & InnerProps;

const Button = (props: Props) => <Inner {...props} />;

export default Button;
