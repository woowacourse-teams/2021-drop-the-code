import Textarea, { Props as TextareaProps } from "components/shared/Textarea/Textarea";
import useFormContext from "hooks/useFormContext";

export interface Props extends TextareaProps {
  name: string;
}

const TextareaField = ({ name, ...props }: Props) => {
  const { values, onChange, errorMessages } = useFormContext();

  return (
    <Textarea value={values[name]} name={name} errorMessage={errorMessages[name]} onChange={onChange} {...props} />
  );
};

export default TextareaField;
