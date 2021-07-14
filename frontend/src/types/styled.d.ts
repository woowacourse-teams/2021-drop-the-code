import "styled-components";

export interface COLOR_TYPE {
  primary: {
    normal: string;
    active: string;
    text: string;
  };
  transParent: {
    normal: string;
    text: string;
  };
}

export interface SHAPE_TYPE {
  rounded: string;
  pill: string;
  circle: string;
}

declare module "styled-components" {
  export interface DefaultTheme {
    color: COLOR_SHAPE;
    shape: SHAPE_TYPE;
    zIndex: {
      modal: number;
    };
  }
}
