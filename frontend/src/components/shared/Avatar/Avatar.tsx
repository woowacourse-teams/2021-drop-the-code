import { ImgHTMLAttributes } from "react";

import styled from "styled-components";

import { SHAPE_TYPE } from "../../../types/styled";

interface InnerProps {
  imageUrl?: string;
  description?: string;
  width?: string;
  height?: string;
  shape?: keyof SHAPE_TYPE;
}

const Inner = styled.img<InnerProps>`
  ${({ width }) => (width ? `width: ${width}` : "")};
  ${({ height }) => (height ? `height: ${height}` : "")};
  ${({ theme, shape = "circle" }) => `border-radius: ${theme.shape[shape]}`};
`;

export type Props = ImgHTMLAttributes<HTMLImageElement> & InnerProps;

const Avatar = ({ imageUrl, description, ...props }: Props) => <Inner src={imageUrl} alt={description} {...props} />;

export default Avatar;
