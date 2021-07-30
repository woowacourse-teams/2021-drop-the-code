import React, { Suspense } from "react";
import ReactDOM from "react-dom";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter } from "react-router-dom";

import axios from "axios";
import { ThemeProvider } from "styled-components";

import App from "App";
import AuthProvider from "components/Auth/AuthProvider/AuthProvider";
import ErrorBoundary from "components/Error/ErrorBoundary";
import GlobalStyle from "components/GlobalStyle/GlobalStyle";
import Loading from "components/Loading/Loading";
import ModalProvider from "components/ModalProvider/ModalProvider";
import ToastProvider from "components/ToastProvider/ToastProvider";
import { THEME } from "utils/constants/theme";

axios.defaults.baseURL = process.env.SERVER_BASE_URL;

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
      <ToastProvider>
        <QueryClientProvider client={queryClient}>
          <Suspense fallback={<Loading />}>
            <AuthProvider>
              <ModalProvider>
                <GlobalStyle />
                <BrowserRouter>
                  <ErrorBoundary>
                    <App />
                  </ErrorBoundary>
                </BrowserRouter>
              </ModalProvider>
            </AuthProvider>
          </Suspense>
        </QueryClientProvider>
      </ToastProvider>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById("root")
);
