import { InputHTMLAttributes } from "react";

import  styled  from 'styled-components';

import { COLOR } from "../../../utils/constants/color";

interface InnerProps {
  placeHolder: string;
  labelText?: string;
} 

const Label = styled.label`
  display: block;
  margin-bottom: 10px;
`

const Inner = styled.input<InnerProps>`
  width: 500px;
  padding: 5px;
  border-radius: 4px;
  border: 1px solid ${COLOR.GRAY_500}
`

export type Props = InputHTMLAttributes<HTMLInputElement> & InnerProps;

const Input = ({labelText, ...props}: Props) => 
<div>
  <Label htmlFor={labelText}>{labelText}</Label>
  <Inner id={labelText} {...props}/>
</div>;

export default Input;