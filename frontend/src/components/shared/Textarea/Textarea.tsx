import { TextareaHTMLAttributes } from "react";

import  styled  from 'styled-components';

import { COLOR } from "../../../utils/constants/color";

interface InnerProps {
  height?: string;
  labelText?: string;
  placeHolder: string;
} 

const Label = styled.label`
  display: block;
  margin-bottom: 10px;
`

const Inner = styled.textarea<InnerProps>`
  width: 500px;
  max-width: 500px;
  ${({height}) => (height ? `height: ${height}` : '')};
  padding: 5px;
  border-radius: 4px;
  line-height: 1.5;
  border: 1px solid ${COLOR.GRAY_500};
`

export type Props = TextareaHTMLAttributes<HTMLTextAreaElement> & InnerProps;

const Textarea = ({labelText, ...props}: Props) => 
<div>
  <Label htmlFor={labelText}>{labelText}</Label>
  <Inner id={labelText} {...props}/>;
</div>

export default Textarea;