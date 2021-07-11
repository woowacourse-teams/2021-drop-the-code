import { createGlobalStyle } from "styled-components";
import normalize from "styled-normalize";
import reset from "styled-reset";

import { COLOR } from "../../utils/constants/color";

const GlobalStyle = createGlobalStyle`
  ${normalize}
  ${reset}

  html {
    font-family: 'Spoqa Han Sans Neo', 'sans-serif'; 
  }

  button {
    border: none;
  }

  header, a {
    color: ${COLOR.WHITE};
  }

  a {
    display: flex;
    align-items: center;
    text-decoration: none;
  }
`;

export default GlobalStyle;
