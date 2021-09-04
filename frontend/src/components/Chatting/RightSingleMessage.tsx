import styled from "styled-components";

import Avatar from "components/shared/Avatar/Avatar";
import { Flex } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

const Inner = styled(Flex)`
  justify-content: flex-end;
  position: relative;
`;

const Message = styled(Flex)`
  max-width: 18.75rem;
  align-self: flex-end;
  flex-wrap: wrap;
  padding: 0.625rem;
  margin: 0.25rem 2.625rem 0.625rem 0.25rem;
  border-radius: 0.625rem;
  line-height: 1.5rem;
  color: ${COLOR.WHITE};
  background-color: ${COLOR.INDIGO_400};
`;

interface Props {
  message: string;
  imageUrl: string;
}

const RightSingleMessage = ({ message, imageUrl }: Props) => {
  return (
    <Inner>
      <Flex css={{ alignItems: "end" }}>
        <Message>{message}</Message>
      </Flex>
      <Avatar
        imageUrl={imageUrl}
        width="2rem"
        height="2rem"
        css={{ position: "absolute", bottom: "0.875rem", marginLeft: "0.625rem" }}
      />
    </Inner>
  );
};

export default RightSingleMessage;
