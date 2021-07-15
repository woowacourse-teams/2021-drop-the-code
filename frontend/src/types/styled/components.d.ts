export type ButtonThemeColor = "primary" | "secondary";

interface BaseColorAttr {
  bg: string;
  color: string;
}

interface ButtonColorAttr extends BaseColorAttr {
  activeBg: string;
}

export type ChipThemeColor = "primary" | "career" | "count" | "averageResponse";

export interface Components {
  button: { [key in ButtonThemeColor]: ButtonColorAttr };
  navigation: BaseColorAttr;
  chip: {
    [key in ChipThemeColor]: BaseColorAttr;
  };
}
