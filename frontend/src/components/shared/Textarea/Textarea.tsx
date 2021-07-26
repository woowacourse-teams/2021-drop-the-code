import { TextareaHTMLAttributes } from "react";

import styled, { css } from "styled-components";

import { COLOR } from "utils/constants/color";

import { Flex } from "../Flexbox/Flexbox";

const Label = styled.label`
  display: block;
  font-weight: 600;
  margin-bottom: 10px;
`;

interface InnerProps {
  isEmpty?: boolean;
  isError?: boolean;
}

const Inner = styled(Flex)<InnerProps>`
  width: 100%;
  flex-direction: column;

  textarea {
    outline: none;
    width: 100%;
    padding: 0.625rem 1.25rem;
    border: 1px solid;
    border-radius: 4px;
    line-height: 2rem;
    white-space: break-spaces;

    border-color: ${({ isEmpty, isError }) => css`
      ${isEmpty ? COLOR.BLACK : isError ? COLOR.RED_500 : COLOR.GREEN_500}
    `};
  }
`;

const ErrorMessage = styled.div`
  padding: 0.3125rem 0;
  font-size: 13px;
  color: ${COLOR.RED_600};
`;

export interface Props extends TextareaHTMLAttributes<HTMLTextAreaElement> {
  labelText?: string;
  errorMessage?: string | null;
}

const Textarea = ({ labelText, errorMessage, value, ...props }: Props) => {
  const innerProps = { ...(labelText && { id: labelText }), value, ...props };

  return (
    <Inner isEmpty={!value} isError={!!errorMessage}>
      {labelText && <Label htmlFor={labelText}>{labelText}</Label>}
      <textarea {...innerProps} />
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
    </Inner>
  );
};

export default Textarea;
