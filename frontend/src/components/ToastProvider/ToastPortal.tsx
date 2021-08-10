import { ReactNode } from "react";
import { createPortal } from "react-dom";

const ToastPortal = (children: ReactNode) => {
  const $toast = document.getElementById("toast");
  if (!$toast) {
    const $toast = document.createElement("div");
    $toast.setAttribute("id", "toast");
    document.body.appendChild($toast);

    return createPortal(children, $toast);
  }

  return createPortal(children, $toast);
};

export default ToastPortal;
