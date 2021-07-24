import { TextareaHTMLAttributes } from "react";

import styled from "styled-components";

import { COLOR } from "utils/constants/color";

interface InnerProps {
  labelText?: string;
  errorMessage?: string | null;
}

const Label = styled.label`
  display: block;
  font-weight: 600;
  margin-bottom: 10px;
`;

const Inner = styled.textarea<InnerProps>`
  width: 100%;
  padding: 0.625rem 1.25rem;
  border-radius: 4px;
  line-height: 2rem;
  border: 1px solid ${COLOR.GRAY_500};
  white-space: break-spaces;
`;

const ErrorMessage = styled.div`
  padding: 0.3125rem 0;
  font-size: 13px;
  color: ${COLOR.RED_600};
`;

export type Props = TextareaHTMLAttributes<HTMLTextAreaElement> & InnerProps;

const Textarea = ({ labelText, errorMessage, ...props }: Props) => {
  const innerProps = { ...(labelText && { id: labelText }), ...props };

  return (
    <>
      {labelText && <Label htmlFor={labelText}>{labelText}</Label>}
      <Inner {...innerProps} />
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
    </>
  );
};

export default Textarea;
