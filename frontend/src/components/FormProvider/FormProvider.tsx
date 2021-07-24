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

  const validate = (name: string, value: string) => {
    const validator = validators?.[name];
    if (!validator) return;

    try {
      validator(value);

      setErrorMessages({ ...errorMessages, [name]: null });
    } catch (error) {
      setErrorMessages({ ...errorMessages, [name]: error.message });
    }

    return;
  };

  const onChange: React.ChangeEventHandler<HTMLInputElement | HTMLSelectElement> = ({ target }) => {
    const { name, value } = target;

    setValues({ ...values, [name]: value });

    validate(name, value);
  };

  const onSubmit: React.FormEventHandler<HTMLFormElement> = (event) => {
    event.preventDefault();

    if (!isValid) return;

    submit(values);
  };

  return (
    <FormContext.Provider value={{ values, errorMessages, onChange, isValid }}>
      <form onSubmit={onSubmit} {...props}>
        {children}
      </form>
    </FormContext.Provider>
  );
};

export default FormProvider;
