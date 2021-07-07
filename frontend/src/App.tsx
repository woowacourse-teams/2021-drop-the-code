import { ThemeProvider } from "styled-components";

import GlobalStyle from "./components/GlobalStyle/GlobalStyle";
import { THEME } from "./utils/constants/theme";

const App = () => {
  return (
    <ThemeProvider theme={THEME}>
      <GlobalStyle />
    </ThemeProvider>
  );
};

export default App;
