import styled from "styled-components";

import Avatar from "components/shared/Avatar/Avatar";
import { Flex } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

const Inner = styled.div`
  position: relative;
`;

const Message = styled.div`
  max-width: 18.75rem;
  align-items: flex-start;
  flex-wrap: wrap;
  padding: 0.625rem;
  margin: 0.25rem 0.25rem 0.625rem 2.625rem;
  border-radius: 0.625rem;
  line-height: 1.5rem;
  background-color: ${COLOR.GRAY_200};
`;

interface Props {
  message: string;
  imageUrl: string;
}

const LeftSingleMessage = ({ message, imageUrl }: Props) => {
  return (
    <Inner>
      <Flex css={{ alignItems: "start" }}>
        <Message>{message}</Message>
      </Flex>
      <Avatar
        imageUrl={imageUrl}
        width="2rem"
        height="2rem"
        css={{ position: "absolute", bottom: "0.3125rem", marginRight: "0.625rem" }}
      />
    </Inner>
  );
};

export default LeftSingleMessage;
