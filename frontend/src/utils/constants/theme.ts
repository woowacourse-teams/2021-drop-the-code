import { DefaultTheme } from "styled-components";

import { Common } from "../../types/styled/common";
import { Components } from "../../types/styled/components";

import { COLOR } from "./color";

const components: Components = {
  button: {
    primary: {
      bg: COLOR.PURPLE_600,
      color: COLOR.WHITE,
      activeBg: COLOR.PURPLE_900,
    },
    secondary: {
      bg: "inherit",
      color: COLOR.GRAY_700,
      activeBg: COLOR.GRAY_200,
    },
  },
  navigation: {
    bg: COLOR.WHITE,
    color: COLOR.GRAY_700,
  },
  chip: {
    primary: {
      bg: COLOR.PURPLE_600,
      color: COLOR.WHITE,
    },
    career: { bg: COLOR.GRAY_450, color: COLOR.WHITE },
    count: {
      bg: COLOR.GRAY_470,
      color: COLOR.WHITE,
    },
    averageResponse: {
      bg: COLOR.PINK_400,
      color: COLOR.WHITE,
    },
  },
};

const common: Common = {
  color: {
    primary: COLOR.PURPLE_600,
    secondary: "inherit",
    dark: COLOR.BLACK,
    light: COLOR.WHITE,
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

export const THEME: DefaultTheme = {
  components,
  common,
};
