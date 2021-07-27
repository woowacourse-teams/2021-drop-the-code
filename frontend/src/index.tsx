import React from "react";
import ReactDOM from "react-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter } from "react-router-dom";

import { ThemeProvider } from "styled-components";

import App from "./App";
import AuthProvider from "./components/Auth/AuthProvider/AuthProvider";
import GlobalStyle from "./components/GlobalStyle/GlobalStyle";
import ModalProvider from "./components/ModalProvider/ModalProvider";
import { THEME } from "./utils/constants/theme";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
    },
  },
});

ReactDOM.render(
  <React.StrictMode>
    <ThemeProvider theme={THEME}>
      <AuthProvider>
        <QueryClientProvider client={queryClient}>
          <ModalProvider>
            <GlobalStyle />
            <BrowserRouter>
              <App />
            </BrowserRouter>
          </ModalProvider>
        </QueryClientProvider>
      </AuthProvider>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById("root")
);
