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

  * {
    box-sizing: border-box;
  }

  button {
    border: none;
    cursor: pointer;
  }

  header, a {
    color: ${COLOR.BLACK};
  }

  a {
    display: flex;
    text-decoration: none;
  }
`;

export default GlobalStyle;
