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
  display: inline-flex;
  margin: 0 auto;
  background-color: ${({ type, theme }) =>
    type === "success" ? theme.common.color.success : theme.common.color.error};
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
  max-width: 12.5rem;
  font-weight: 600;
`;

const Right = styled(Flex)`
  width: 1.875rem;
  padding: 0.625rem 0.625rem 0 0;
`;

const Close = styled(CloseSvg)`
  width: 0.75rem;
  height: 0.75rem;
  margin-left: auto;
  stroke: ${({ theme }) => theme.common.color.light};
  stroke-width: 2px;
  cursor: pointer;
`;

const Toast = ({ id, message, type = "success", removeToast }: Props) => {
  return (
    <Transition initial={initial} exit={exit} duration={[500, 2000, 500]}>
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
