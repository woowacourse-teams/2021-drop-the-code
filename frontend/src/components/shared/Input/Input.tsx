import { InputHTMLAttributes } from "react";

import styled from "styled-components";

import { COLOR } from "../../../utils/constants/color";

interface InnerProps {
  placeHolder: string;
  labelText?: string;
  errorMessage?: string;
}

const Label = styled.label`
  display: block;
  margin-bottom: 10px;
`;

const Inner = styled.input<InnerProps>`
  width: 100%;
  padding: 5px;
  border-radius: 4px;
  border: 1px solid ${COLOR.GRAY_500};
`;

const ErrorMessage = styled.div`
  padding: 5px;
  font-size: 13px;
  color: ${COLOR.RED_600};
`;

export type Props = InputHTMLAttributes<HTMLInputElement> & InnerProps;

const Input = ({ labelText, errorMessage, ...props }: Props) => {
  const innerProps = { ...(labelText && { id: labelText }), ...props };

  return (
    <>
      {labelText && <Label htmlFor={labelText}>{labelText}</Label>}
      <Inner {...innerProps} />
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
    </>
  );
};

export default Input;
