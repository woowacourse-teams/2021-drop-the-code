import { ReactNode, PropsWithChildren } from "react";

import styled from "styled-components";

import { Flex } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

const Inner = styled(Flex)`
  flex-direction: column;
  width: 100%;
  padding: 1.25rem;
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
`;

const Title = styled.div`
  padding-bottom: 0.9375rem;
  border-bottom: 0.0625rem solid ${COLOR.GRAY_400};
  font-weight: 600;
`;

const Description = styled.div`
  margin-top: 0.9375rem;
  line-height: 2rem;
  white-space: break-spaces;
`;

export interface Props {
  titleChildren: ReactNode;
}

const ReviewerTextbox = ({ titleChildren, children, ...props }: PropsWithChildren<Props>) => {
  return (
    <Inner {...props}>
      <Title>{titleChildren}</Title>
      <Description>{children}</Description>
    </Inner>
  );
};

export default ReviewerTextbox;
