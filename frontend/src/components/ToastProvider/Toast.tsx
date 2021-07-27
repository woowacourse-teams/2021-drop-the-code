import styled, { CSSObject } from "styled-components";

import CloseSvg from "assets/close.svg";
import { Flex } from "components/shared/Flexbox/Flexbox";
import Transition from "components/Transititon/Transition";

export interface Options {
  type?: "success" | "error";
}

export interface Props extends Options {
  id: number;
  message?: string | null;
  open?: boolean;
  removeToast: (id: number) => void;
}

type InnerProps = Pick<Props, "type">;

const initial: CSSObject = {
  transform: "scale(1, 1)",
  opacity: 1,
};

const exit: CSSObject = {
  transform: "scale(.8, .8)",
  opacity: 0,
};

const Inner = styled.li<InnerProps>`
  display: flex;
  margin: 0 auto;
  background-color: ${({ type, theme }) => (type === "success" ? "green" : "red")};
  color: ${({ theme }) => theme.common.color.light};
  opacity: 0;

  border-radius: ${({ theme }) => theme.common.shape.rounded};
  transform: translateY(100%);

  transition: transform 1s, opacity 1s;
`;

const Left = styled(Flex)`
  width: 1.875rem;
`;

const Message = styled.p`
  padding: 1.25rem 0;
  min-width: 9.375rem;
  max-width: 12.5rem;
`;

const Right = styled(Flex)`
  width: 3.125rem;
  padding: 0.625rem 0.625rem 0 0;
`;

const Close = styled(CloseSvg)`
  width: 0.75rem;
  height: 0.75rem;
  margin-left: auto;
  stroke: ${({ theme }) => theme.common.color.light};
  stroke-width: 1px;
  cursor: pointer;
`;

const Toast = ({ id, message, type = "success", removeToast }: Props) => {
  return (
    <Transition initial={initial} exit={exit} duration={[500, 1111111, 500]}>
      {({ css, onClose }) => {
        return (
          <Inner type={type} css={css}>
            <Left />
            <Message>{message}</Message>
            <Right>
              <Close
                onClick={() => {
                  onClose(() => removeToast(id));
                }}
              />
            </Right>
          </Inner>
        );
      }}
    </Transition>
  );
};

export default Toast;
