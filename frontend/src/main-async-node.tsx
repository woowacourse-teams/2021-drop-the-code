import React from "react";
import { QueryClient, QueryClientProvider } from "react-query";

import { ThemeProvider } from "styled-components";

import App from "App";
import AuthProvider from "components/Auth/AuthProvider/AuthProvider";
import GlobalStyle from "components/GlobalStyle/GlobalStyle";
import ModalProvider from "components/ModalProvider/ModalProvider";
import ToastProvider from "components/ToastProvider/ToastProvider";
import { THEME } from "theme/theme";

const queryClient = new QueryClient();

// eslint-disable-next-line react/display-name
export default () => (
  <React.StrictMode>
    <ThemeProvider theme={THEME}>
      <ToastProvider>
        <QueryClientProvider client={queryClient}>
          <AuthProvider>
            <ModalProvider>
              <GlobalStyle />
              <App />
            </ModalProvider>
          </AuthProvider>
        </QueryClientProvider>
      </ToastProvider>
    </ThemeProvider>
  </React.StrictMode>
);
