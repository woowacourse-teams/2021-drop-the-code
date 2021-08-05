import styled from "styled-components";

import { Flex } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

const Inner = styled(Flex)`
  flex-direction: column;
  padding: 2.5rem;
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
  border-radius: ${({ theme }) => theme.common.shape.rounded};
`;

const Title = styled.div`
  padding: 0.625rem 0;
  font-weight: 900;
  border-bottom: 1px solid ${COLOR.GRAY_300};
`;

const Content = styled.div`
  margin-top: 0.9375rem;
  white-space: break-spaces;
  color: ${COLOR.GRAY_700};
  font-size: 0.875rem;
  line-height: 1.25rem;
`;

export interface Props {
  title?: React.ReactNode;
  children?: React.ReactNode;
}

const ContextBox = ({ title, children, ...props }: Props) => {
  return (
    <Inner {...props}>
      <Title>{title}</Title>
      <Content>{children}</Content>
    </Inner>
  );
};

export default ContextBox;
