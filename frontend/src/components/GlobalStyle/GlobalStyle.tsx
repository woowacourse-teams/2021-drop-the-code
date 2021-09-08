import { createGlobalStyle } from "styled-components";
import reset from "styled-reset";

import { COLOR } from "utils/constants/color";

const GlobalStyle = createGlobalStyle`
  ${reset}

  @font-face {
    font-family: 'IBMPlexSansKR-Regular';
    src: url(${process.env.CDN_URL + "/IBMPlexSansKR-Regular.woff2"}) format('woff2'),
    url(${process.env.CDN_URL + "/IBMPlexSansKR-Regular.woff"}) format('woff');
 
    font-weight: normal;
    font-style: normal;
  }

  html {
    font-family: 'IBMPlexSansKR-Regular', 'sans-serif'; 
    overflow-y: scroll;
  }

  * {
    box-sizing: border-box;
    word-break: break-all;
  }

  button {
    border: none;
    cursor: pointer;
  }

  a {
    display: flex;
    color: ${COLOR.BLACK};
    text-decoration: none;
  }

  h2 {
    font-size: 1.625rem;
    font-weight: 600;
    margin: 1.5rem 0;
  }

  button {
    background: none;
  }

  main {
    padding-top: 4rem; 
    width: 100%; 
    max-width: ${({ theme }) => theme.common.layout.lg}; 
    margin: 0 auto; 
  }
`;

export default GlobalStyle;
