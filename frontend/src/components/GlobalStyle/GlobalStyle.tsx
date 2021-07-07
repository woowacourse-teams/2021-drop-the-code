import { createGlobalStyle } from "styled-components";
import normalize from "styled-normalize";
import reset from "styled-reset";

const GlobalStyle = createGlobalStyle`
  ${normalize}
  ${reset}

  button {
    border: none;
  }
`;

export default GlobalStyle;
