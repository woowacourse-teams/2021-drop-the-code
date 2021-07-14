import { createContext, useContext } from "react";

interface ModalContextProps {
  open: (children: React.ReactNode) => void;
  close: () => void;
}

export const ModalContext = createContext<ModalContextProps | null>(null);

const useModalContext = () => {
  const context = useContext(ModalContext);

  if (!context) throw Error("ModalContext가 존재하지 않습니다");

  return context;
};

export default useModalContext;
