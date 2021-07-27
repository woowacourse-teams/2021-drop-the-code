import { QueryClient, QueryClientProvider } from "react-query";
import { MemoryRouter } from "react-router";

import axios from "axios";
import { ThemeProvider } from "styled-components";

import GlobalStyle from "components/GlobalStyle/GlobalStyle";
import { THEME } from "utils/constants/theme";

import ModalProvider from "components/ModalProvider/ModalProvider";
import AuthProvider from "components/Auth/AuthProvider/AuthProvider";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import { Suspense } from "react";

import Loading from "components/Loading/Loading";

axios.defaults.baseURL = process.env.SERVER_BASE_URL;

export const parameters = {
  actions: { argTypesRegex: "^on[A-Z].*" },
  controls: {
    matchers: {
      color: /(background|color)$/i,
      date: /Date$/,
    },
  },
  layout: "centered",
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
            <AuthProvider>
              <ModalProvider>
                <FlexCenter>
                  <Story />
                </FlexCenter>
              </ModalProvider>
            </AuthProvider>
          </Suspense>
        </MemoryRouter>
      </QueryClientProvider>
    </ThemeProvider>
  ),
];
