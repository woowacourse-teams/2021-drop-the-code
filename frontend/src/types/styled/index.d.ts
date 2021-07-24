import "styled-components";

import { Common } from "types/styled/common";
import { Components } from "types/styled/components";

declare module "styled-components" {
  export interface DefaultTheme {
    common: Common;
    components: Components;
  }
}
