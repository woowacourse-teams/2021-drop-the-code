import { createContext, useContext } from "react";

interface FormContextProps {
  values: { [name: string]: string };
  errorMessages: { [name: string]: string | null };
  isValid: boolean;
  isEmpty: boolean;
  register: (name: string) => void;
  onChange: React.ChangeEventHandler;
}

export const FormContext = createContext<FormContextProps | null>(null);

const useFormContext = () => {
  const context = useContext(FormContext);

  if (!context) throw Error("FormContext가 존재하지 않습니다");

  return context;
};

export default useFormContext;
