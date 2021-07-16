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
      color: COLOR.BLACK,
    },
    career: { bg: COLOR.RED_350, color: COLOR.BLACK },
    count: {
      bg: COLOR.GREEN_350,
      color: COLOR.BLACK,
    },
    averageResponse: {
      bg: COLOR.TEAL_350,
      color: COLOR.BLACK,
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
