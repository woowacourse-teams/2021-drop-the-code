import { InputHTMLAttributes } from "react";

import styled from "styled-components";

import { Flex } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

interface InnerProps {
  labelText?: string;
  errorMessage?: string | null;
}

const Label = styled.label`
  display: block;
  font-weight: 600;
  margin-bottom: 0.625rem;
`;

const Inner = styled(Flex)`
  width: 100%;
  flex-direction: column;

  input {
    width: 100%;
    padding: 0.625rem 1.25rem;
    border-radius: 4px;
    border: 1px solid ${COLOR.GRAY_500};
  }
`;

const ErrorMessage = styled.div`
  padding: 0.3125rem 0;
  font-size: 0.8125rem;
  color: ${COLOR.RED_600};
`;

export type Props = InputHTMLAttributes<HTMLInputElement> & InnerProps;

const Input = ({ labelText, errorMessage, ...props }: Props) => {
  const innerProps = { ...(labelText && { id: labelText }), ...props };

  return (
    <Inner>
      {labelText && <Label htmlFor={labelText}>{labelText}</Label>}
      <input {...innerProps} />
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
    </Inner>
  );
};

export default Input;
