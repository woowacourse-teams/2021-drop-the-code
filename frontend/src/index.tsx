import { hydrateRoot, createRoot } from "react-dom";
import { BrowserRouter } from "react-router-dom";

import App from "App";

const container = document.getElementById("root");

if (container) {
  if (process.env.NODE_ENV === "production") {
    hydrateRoot(
      container,
      <BrowserRouter>
        <App />
      </BrowserRouter>
    );
  } else {
    createRoot(container).render(
      <BrowserRouter>
        <App />
      </BrowserRouter>
    );
  }
}
