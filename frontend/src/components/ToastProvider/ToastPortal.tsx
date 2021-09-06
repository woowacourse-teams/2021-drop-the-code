import { ReactNode, useEffect, useState } from "react";
import { createPortal } from "react-dom";

const ToastPortal = (children: ReactNode) => {
  const [toast, setToast] = useState<HTMLElement | null>(null);

  useEffect(() => {
    const toast = document.getElementById("toast");
    if (!toast) {
      const toast = document.createElement("div");
      toast.setAttribute("id", "toast");
      document.body.appendChild(toast);
      setToast(toast);

      return;
    }

    setToast(toast);
  }, []);

  if (!toast) return null;

  return createPortal(children, toast);
};

export default ToastPortal;
