import { MemoryRouter } from "react-router";
import { ThemeProvider } from "styled-components";

import GlobalStyle from "../src/components/GlobalStyle/GlobalStyle";
import { THEME } from "../src/utils/constants/theme";

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

export const decorators = [
  (Story) => (
    <ThemeProvider theme={THEME}>
      <GlobalStyle />
      <MemoryRouter>
        <Story />
      </MemoryRouter>
    </ThemeProvider>
  ),
];
