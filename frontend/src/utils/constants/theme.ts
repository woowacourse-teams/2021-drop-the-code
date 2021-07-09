import { DefaultTheme } from "styled-components";

import { COLOR } from "./color";

export const THEME: DefaultTheme = {
  color: {
    primary: { normal: COLOR.PURPLE_600, active: COLOR.PURPLE_900, text: COLOR.WHITE },
    white: { normal: COLOR.WHITE, active: COLOR.GRAY_200, text: COLOR.GRAY_700 },
  },
};

export type COLOR_TYPE = keyof typeof THEME.color;
