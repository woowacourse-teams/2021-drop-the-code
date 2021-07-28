import styled from "styled-components";

import { Flex } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

const Inner = styled(Flex)`
  flex-direction: column;
  padding: 2.5rem;
  box-shadow: 0.4375rem 0.375rem 0.375rem rgb(0 0 0 / 10%);
  border-radius: ${({ theme }) => theme.common.shape.rounded};
`;

const Title = styled.div`
  padding: 0.625rem 0;
  font-weight: 900;
  border-bottom: 1px solid ${COLOR.GRAY_300};
`;

const Content = styled.p`
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

const ContextBox = ({ title, children }: Props) => {
  return (
    <Inner>
      <Title>{title}</Title>
      <Content>{children}</Content>
    </Inner>
  );
};

export default ContextBox;
