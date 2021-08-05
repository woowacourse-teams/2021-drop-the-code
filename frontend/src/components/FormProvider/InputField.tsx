import { useEffect } from "react";

import Input, { Props as InputProps } from "components/shared/Input/Input";
import useFormContext from "hooks/useFormContext";

export interface Props extends InputProps {
  name: string;
  initialValue?: string | number;
}

const InputField = ({ name, initialValue, ...props }: Props) => {
  const { values, errorMessages, register, onChange } = useFormContext();

  useEffect(() => {
    register(name, initialValue);
  }, []);

  return (
    <Input value={values[name] || ""} name={name} errorMessage={errorMessages[name]} onChange={onChange} {...props} />
  );
};

export default InputField;
