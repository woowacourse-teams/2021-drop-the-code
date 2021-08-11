import React from "react";
import ReactDOM from "react-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter } from "react-router-dom";

import axios from "axios";
import { ThemeProvider } from "styled-components";

import App from "App";
import AuthProvider from "components/Auth/AuthProvider/AuthProvider";
import GlobalStyle from "components/GlobalStyle/GlobalStyle";
import ModalProvider from "components/ModalProvider/ModalProvider";
import ToastProvider from "components/ToastProvider/ToastProvider";
import { THEME } from "theme/theme";

axios.defaults.baseURL = process.env.SERVER_BASE_URL;

const queryClient = new QueryClient();

ReactDOM.render(
  <React.StrictMode>
    <ThemeProvider theme={THEME}>
      <ToastProvider>
        <QueryClientProvider client={queryClient}>
          <AuthProvider>
            <ModalProvider>
              <GlobalStyle />
              <BrowserRouter>
                <App />
              </BrowserRouter>
            </ModalProvider>
          </AuthProvider>
        </QueryClientProvider>
      </ToastProvider>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById("root")
);
