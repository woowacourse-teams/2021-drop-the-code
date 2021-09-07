import { Link } from "react-router-dom";

import styled from "styled-components";
import { Reviewer } from "types/reviewer";

import { COLOR } from "utils/constants/color";

const Inner = styled(Link)`
  position: -webkit-sticky;
  position: sticky;
  justify-content: center;
  padding: 1rem;
  margin-top: 0.625rem;
  font-size: 14px;
  color: ${COLOR.WHITE};
  font-weight: 900;
  background-color: ${({ theme }) => theme.common.color.primary};
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
`;

export interface Props {
  reviewer: Reviewer;
}

const FloatingMessageButton = ({ reviewer }: Props) => {
  return <Inner to="/chatting">💬 {reviewer.name}에게 메세지 보내기</Inner>;
};

export default FloatingMessageButton;
