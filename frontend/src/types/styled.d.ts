import "styled-components";

declare module "styled-components" {
  export interface DefaultTheme {
    color: {
      primary: {
        normal: string;
        active: string;
        text: string;
      };
    };
  }
}
