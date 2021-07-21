import { QueryClient, QueryClientProvider } from "react-query";
import { MemoryRouter } from "react-router";

import axios from "axios";
import { ThemeProvider } from "styled-components";

import GlobalStyle from "../src/components/GlobalStyle/GlobalStyle";
import { THEME } from "../src/utils/constants/theme";

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
          <Story />
        </MemoryRouter>
      </QueryClientProvider>
    </ThemeProvider>
  ),
];
