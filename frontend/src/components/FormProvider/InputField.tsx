import Input, { Props as InputProps } from "components/shared/Input/Input";
import useFormContext from "hooks/useFormContext";

export interface Props extends InputProps {
  name: string;
}

const InputField = ({ name, ...props }: Props) => {
  const { values, onChange, errorMessages } = useFormContext();

  return (
    <Input value={values[name] || ""} name={name} errorMessage={errorMessages[name]} onChange={onChange} {...props} />
  );
};

export default InputField;
