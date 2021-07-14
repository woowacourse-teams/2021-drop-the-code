import { DefaultTheme } from "styled-components";

import { COLOR } from "./color";

export const THEME: DefaultTheme = {
  color: {
    primary: { normal: COLOR.PURPLE_600, active: COLOR.PURPLE_900, text: COLOR.WHITE },
    transParent: { normal: "inherit", text: COLOR.GRAY_700 },
  },
  shape: {
    rounded: "0.25rem",
    pill: "6.25rem",
    circle: "100%",
  },
  zIndex: {
    modal: 99,
  },
};
