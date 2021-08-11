import { ReactNode, useEffect, useState } from "react";
import { createPortal } from "react-dom";

const ModalPortal = (children: ReactNode) => {
  const [modal, setModal] = useState<HTMLElement | null>(null);

  useEffect(() => {
    const modal = document.getElementById("modal");
    if (!modal) {
      const modal = document.createElement("div");
      modal.setAttribute("id", "modal");
      document.body.appendChild(modal);
      setModal(modal);

      return;
    }

    setModal(modal);
  }, []);

  if (!modal) return null;

  return createPortal(children, modal);
};

export default ModalPortal;
