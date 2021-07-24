import { createGlobalStyle } from "styled-components";
import normalize from "styled-normalize";
import reset from "styled-reset";

import { COLOR } from "utils/constants/color";

const GlobalStyle = createGlobalStyle`
  ${normalize}
  ${reset}

  @font-face {
    font-family: 'IBMPlexSansKR-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
  }

  html {
    font-family: 'IBMPlexSansKR-Regular', 'sans-serif'; 
  }

  * {
    box-sizing: border-box;
    word-break: break-word;
  }

  button {
    border: none;
    cursor: pointer;
  }

  header, a {
    color: ${COLOR.BLACK};
    cursor: pointer;
  }

  a {
    display: flex;
    text-decoration: none;
  }

  h2 {
    font-size: 1.25rem;
    font-weight: 600;
    margin: 2.5rem 0;
  }

  button {
    background: none;
  }
`;

export default GlobalStyle;
