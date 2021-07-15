import { ReactNode } from "react";

import styled from "styled-components";

import { COLOR } from "../../../utils/constants/color";
import { Flex } from "../Flexbox/Flexbox";

const Inner = styled(Flex)`
  flex-direction: column;
  width: 100%;
  padding: 20px;
  box-shadow: 13px 13px 35px 5px rgb(0 0 0 / 20%);
`;

const Title = styled.div`
  padding-bottom: 0.9375rem;
  border-bottom: 0.0625rem solid ${COLOR.GRAY_400};
  font-weight: 600;
`;

const Description = styled.div`
  margin-top: 0.9375rem;
  line-height: 1.5rem;
  white-space: break-spaces;
`;

export interface Props {
  title: ReactNode;
  description: ReactNode;
}

const ReviewerTextbox = ({ title, description, ...props }: Props) => {
  return (
    <Inner {...props}>
      <Title>{title}</Title>
      <Description>{description}</Description>
    </Inner>
  );
};

export default ReviewerTextbox;
