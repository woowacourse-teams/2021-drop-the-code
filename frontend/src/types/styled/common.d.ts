export type ThemeColor = "primary" | "secondary" | "dark" | "light";

export interface Shape {
  rounded: string;
  pill: string;
  circle: string;
}

export type ZIndex = "modal";

export interface Common {
  color: { [key in ThemeColor]: string };
  shape: Shape;
  zIndex: { [key in ZIndex]: number };
}
