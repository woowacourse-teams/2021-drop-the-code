import { HTMLAttributes } from "react";

import styled from "styled-components";

import { SHAPE_TYPE } from "../../../types/styled";
import { FlexCenter } from "../Flexbox/Flexbox";

interface InnerProps {
  color: string;
  backgroundColor: string;
  shape?: keyof SHAPE_TYPE;
}

const Inner = styled(FlexCenter)<InnerProps>`
  border-radius: ${({ theme, shape = "rounded" }) => theme.shape[shape]};
  padding: 0.5rem;
  color: ${({ color }) => color};
  background-color: ${({ backgroundColor }) => backgroundColor};
`;

export type Props = HTMLAttributes<HTMLDivElement> & InnerProps;

const Chip = (props: Props) => <Inner {...props} />;

export default Chip;
