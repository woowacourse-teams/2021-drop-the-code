import { InputHTMLAttributes } from "react";

import styled, { css } from "styled-components";

import { Flex } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

const Label = styled.label`
  display: block;
  font-weight: 600;
  margin-bottom: 0.625rem;
`;

interface InnerProps {
  isEmpty?: boolean;
  isError?: boolean;
}

const Inner = styled(Flex)<InnerProps>`
  width: 100%;
  flex-direction: column;

  input {
    outline: none;
    width: 100%;
    padding: 0.625rem 1.25rem;
    border: 1px solid;
    border-radius: 4px;
    border-color: ${({ isEmpty, isError, theme }) => css`
      ${isEmpty ? COLOR.BLACK : isError ? theme.common.color.error : theme.common.color.success}
    `};
  }
`;

const ErrorMessage = styled.div`
  padding: 0.3125rem 0;
  font-size: 0.8125rem;
  color: ${COLOR.RED_600};
`;

export interface Props extends InputHTMLAttributes<HTMLInputElement> {
  labelText?: string;
  errorMessage?: string | null;
}

const Input = ({ labelText, errorMessage, value, ...props }: Props) => {
  const innerProps = { ...(labelText && { id: labelText }), value, ...props };

  return (
    <Inner isEmpty={!value} isError={!!errorMessage}>
      {labelText && <Label htmlFor={labelText}>{labelText}</Label>}
      <input {...innerProps} />
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
    </Inner>
  );
};

export default Input;
