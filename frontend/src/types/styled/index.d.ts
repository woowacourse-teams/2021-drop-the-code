import "styled-components";

import { Common } from "./common";
import { Components } from "./components";

declare module "styled-components" {
  export interface DefaultTheme {
    common: Common;
    components: Components;
  }
}
