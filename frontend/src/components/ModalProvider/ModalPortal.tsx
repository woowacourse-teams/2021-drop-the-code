import { ReactNode } from "react";
import { createPortal } from "react-dom";

const ModalPortal = (children: ReactNode) => {
  const $modal = document.getElementById("modal");
  if (!$modal) {
    const $modal = document.createElement("div");
    $modal.setAttribute("id", "modal");
    document.body.appendChild($modal);

    return createPortal(children, $modal);
  }

  return createPortal(children, $modal);
};

export default ModalPortal;
