import { QueryClient, QueryClientProvider } from "react-query";
import { MemoryRouter } from "react-router";

import axios from "axios";
import { ThemeProvider } from "styled-components";

import GlobalStyle from "../src/components/GlobalStyle/GlobalStyle";
import { THEME } from "../src/utils/constants/theme";

import ModalProvider from "../src/components/ModalProvider/ModalProvider";
import AuthProvider from "../src/components/Auth/AuthProvider/AuthProvider";
import { LAYOUT } from "../src/utils/constants/size";
import { FlexCenter } from "../src/components/shared/Flexbox/Flexbox";

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
          <AuthProvider>
            <ModalProvider>
              <FlexCenter>
                <Story />
              </FlexCenter>
            </ModalProvider>
          </AuthProvider>
        </MemoryRouter>
      </QueryClientProvider>
    </ThemeProvider>
  ),
];
