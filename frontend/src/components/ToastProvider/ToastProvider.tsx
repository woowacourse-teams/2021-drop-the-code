import { useState } from "react";

import styled from "styled-components";

import { ToastContext } from "hooks/useToastContext";

import Toast, { Options } from "./Toast";

interface Props {
  children: React.ReactNode;
}

interface ToastState extends Options {
  id: number;
  message?: string;
}

const Ul = styled.ul`
  display: flex;
  position: fixed;
  bottom: 0;
  flex-direction: column;

  > li {
    margin-bottom: 0.3125rem;
  }
`;

const ToastProvider = ({ children }: Props) => {
  const [toasts, setToasts] = useState<ToastState[]>([]);
  const [id, setId] = useState(0);

  const createToast = (message: string, options?: Options) => {
    setToasts((prevToasts) => [...prevToasts, { ...options, message, id }]);
    setId((prevId) => prevId + 1);
  };

  const removeToast = (toastId: number) => {
    setToasts((prevToasts) => prevToasts.filter(({ id }) => id !== toastId));
    setId((prevId) => prevId - 1);
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
