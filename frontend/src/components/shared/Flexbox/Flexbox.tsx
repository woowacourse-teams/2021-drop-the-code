import styled from "styled-components";

export const Flex = styled.div`
  display: flex;
`;

export const FlexAlignCenter = styled(Flex)`
  align-items: center;
`;

export const FlexCenter = styled(FlexAlignCenter)`
  justify-content: center;
`;

export const FlexSpaceBetween = styled(Flex)`
  justify-content: space-between;
`;

export const FlexEnd = styled(Flex)`
  justify-content: flex-end;
`;
