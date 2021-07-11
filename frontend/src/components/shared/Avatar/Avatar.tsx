import { ImgHTMLAttributes } from "react";

import styled from "styled-components";

interface InnerProps {
  imageUrl?: string;
  description?: string;
  width?: string;
  height?: string;
}

const Inner = styled.img<InnerProps>`
  ${({ width }) => (width ? `width: ${width}` : "")};
  ${({ height }) => (height ? `height: ${height}` : "")};
  border-radius: 15px;
`;

export type Props = ImgHTMLAttributes<HTMLImageElement> & InnerProps;

const Avatar = ({ imageUrl, description, ...props }: Props) => <Inner src={imageUrl} alt={description} {...props} />;

export default Avatar;
