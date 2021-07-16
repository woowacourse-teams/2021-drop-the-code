import React, { useState, ReactNode } from "react";

import { ModalContext } from "../../hooks/useModalContext";

import Modal from "./Modal";

export interface Props {
  children: ReactNode;
}

const ModalProvider = ({ children }: Props) => {
  const [isOpen, setOpen] = useState(false);
  const [modalChildren, setModalChildren] = useState<React.ReactNode>(null);

  const open = (modalChildren: ReactNode) => {
    setModalChildren(modalChildren);
    setOpen(true);
  };

  const close = () => {
    setModalChildren(null);
    setOpen(false);
  };

  return (
    <ModalContext.Provider
      value={{
        open,
        close,
      }}
    >
      {children}
      {isOpen && <Modal>{modalChildren}</Modal>}
    </ModalContext.Provider>
  );
};

export default ModalProvider;
