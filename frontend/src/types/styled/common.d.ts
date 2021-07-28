export type ThemeColor = "primary" | "secondary" | "dark" | "light" | "success" | "error";

export interface Shape {
  rounded: string;
  pill: string;
  circle: string;
}

export type ZIndex = "toast" | "modal" | "header" | "menuItem";

export interface Common {
  color: { [key in ThemeColor]: string };
  shape: Shape;
  zIndex: { [key in ZIndex]: number };
}
