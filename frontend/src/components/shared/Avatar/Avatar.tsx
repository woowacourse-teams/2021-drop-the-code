import { ImgHTMLAttributes } from "react";

import styled from "styled-components";
import { Shape } from "types/styled/common";

interface InnerProps {
  imageUrl?: string;
  description?: string;
  width?: string;
  height?: string;
  shape?: keyof Shape;
}

const Inner = styled.img<InnerProps>`
  ${({ width }) => (width ? `width: ${width}` : "")};
  ${({ height }) => (height ? `height: ${height}` : "")};
  ${({ theme, shape = "circle" }) => `border-radius: ${theme.common.shape[shape]}`};
`;

export type Props = ImgHTMLAttributes<HTMLImageElement> & InnerProps;

const Avatar = ({ imageUrl, description, ...props }: Props) => <Inner src={imageUrl} alt={description} {...props} />;

export default Avatar;
