import { Suspense } from "react";
import { QueryClient, QueryClientProvider } from "react-query";
import { MemoryRouter } from "react-router";

import axios from "axios";
import { ThemeProvider } from "styled-components";

import GlobalStyle from "components/GlobalStyle/GlobalStyle";
import ModalProvider from "components/ModalProvider/ModalProvider";
import AuthProvider from "components/Auth/AuthProvider/AuthProvider";
import ToastProvider from "components/ToastProvider/ToastProvider";
import Loading from "components/Loading/Loading";
import { THEME } from "theme/theme";

axios.defaults.baseURL = process.env.SERVER_BASE_URL;

export const parameters = {
  actions: { argTypesRegex: "^on[A-Z].*" },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
};

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
    },
  },
});

export const decorators = [
  (Story) => (
    <ThemeProvider theme={THEME}>
      <QueryClientProvider client={queryClient}>
        <GlobalStyle />
        <MemoryRouter>
          <Suspense fallback={<Loading />}>
            <ToastProvider>
              <AuthProvider>
                <ModalProvider>
                  <main>
                    <Story />
                  </main>
                </ModalProvider>
              </AuthProvider>
            </ToastProvider>
          </Suspense>
        </MemoryRouter>
      </QueryClientProvider>
    </ThemeProvider>
  ),
];
