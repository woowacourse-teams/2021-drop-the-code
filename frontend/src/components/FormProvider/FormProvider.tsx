import { useState, FormHTMLAttributes } from "react";

import { FormContext } from "hooks/useFormContext";

interface Values {
  [key: string]: string;
}

interface Props extends Omit<FormHTMLAttributes<HTMLFormElement>, "onSubmit"> {
  children: React.ReactNode;
  submit: (values: Values) => void;
  validators?: { [name: string]: (value: string) => void };
}

const FormProvider = ({ submit, validators, children, ...props }: Props) => {
  const [values, setValues] = useState<Values>({});
  const [errorMessages, setErrorMessages] = useState<{
    [name: string]: string | null;
  }>({});

  const isValid = Object.values(errorMessages).filter(Boolean).length === 0;
  const isEmpty = Object.values(values).filter(Boolean).length < Object.values(values).length;

  const validate = (name: string, value: string) => {
    const validator = validators?.[name];
    if (!validator) return;

    try {
      validator(value);

      setErrorMessages((prevState) => ({ ...prevState, [name]: null }));
    } catch (error: any) {
      setErrorMessages((prevState) => ({ ...prevState, [name]: error.message }));
    }

    return;
  };

  const onChange: React.ChangeEventHandler<HTMLInputElement | HTMLSelectElement> = ({ target }) => {
    const { name, value } = target;

    setValues((prevState) => ({ ...prevState, [name]: value }));

    validate(name, value);
  };

  const onSubmit: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    if (!isValid) return;

    submit(values);
    setValues({});
  };

  const register = (name: string, initialValue: string | number | undefined = "") => {
    setValues((prevState) => ({ ...prevState, [name]: String(initialValue) }));
  };

  return (
    <FormContext.Provider value={{ values, errorMessages, isValid, isEmpty, register, onChange }}>
      <form onSubmit={onSubmit} {...props} css={{ width: "100%" }}>
        {children}
      </form>
    </FormContext.Provider>
  );
};

export default FormProvider;
