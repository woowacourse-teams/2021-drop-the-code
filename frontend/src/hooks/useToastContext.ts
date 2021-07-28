import { createContext, useContext } from "react";

import { Props as ToastProps } from "components/ToastProvider/Toast";

type createToast = (message: string, options?: Pick<ToastProps, "type">) => void;

export const ToastContext = createContext<createToast | null>(null);

const useToastContext = () => {
  const context = useContext(ToastContext);

  if (!context) throw Error("ToastContext가 존재하지 않습니다");

  return context;
};

export default useToastContext;
