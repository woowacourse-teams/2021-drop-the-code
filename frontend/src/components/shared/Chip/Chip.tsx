import { HTMLAttributes } from "react";

import styled from "styled-components";
import { Shape } from "types/styled/common";
import { ChipThemeColor } from "types/styled/components";

interface InnerProps {
  themeColor?: ChipThemeColor;
  shape?: keyof Shape;
}

const Inner = styled.div<InnerProps>`
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border-radius: ${({ theme, shape = "rounded" }) => theme.common.shape[shape]};
  padding: 0.375rem 0.5rem;
  color: ${({ theme, themeColor = "primary" }) => theme.components.chip[themeColor].color};
  background-color: ${({ theme, themeColor = "primary" }) => theme.components.chip[themeColor].bg};
`;

export type Props = HTMLAttributes<HTMLDivElement> & InnerProps;

const Chip = (props: Props) => <Inner {...props} />;

export default Chip;
