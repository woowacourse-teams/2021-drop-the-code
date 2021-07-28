import { useState } from "react";

import { nanoid } from "nanoid";
import styled from "styled-components";

import { ToastContext } from "hooks/useToastContext";

import Toast, { Options } from "./Toast";

interface Props {
  children: React.ReactNode;
}

interface ToastState extends Options {
  id: string;
  message?: string;
}

const Ul = styled.ul`
  display: flex;
  position: fixed;
  bottom: 0;
  flex-direction: column;
  z-index: ${({ theme }) => theme.common.zIndex.toast};

  > li {
    margin-bottom: 0.3125rem;
  }
`;

const ToastProvider = ({ children }: Props) => {
  const [toasts, setToasts] = useState<ToastState[]>([]);

  const createToast = (message: string, options?: Options) => {
    setToasts((prevToasts) => [...prevToasts, { ...options, message, id: nanoid() }]);
  };

  const removeToast = (toastId: string) => {
    setToasts((prevToasts) => prevToasts.filter(({ id }) => id !== toastId));
  };

  return (
    <ToastContext.Provider value={createToast}>
      {children}
      <Ul>
        {toasts.map((toast) => (
          <Toast key={toast.id} {...toast} removeToast={removeToast} />
        ))}
      </Ul>
    </ToastContext.Provider>
  );
};

export default ToastProvider;
