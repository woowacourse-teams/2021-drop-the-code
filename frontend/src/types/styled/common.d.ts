export interface Common {
  color: { [key in ThemeColor]: string };
  boxShadow: { [key in BoxShadowTheme]: string };
  shape: Shape;
  zIndex: { [key in ZIndex]: number };
  layout: Layout;
}

export type ThemeColor = "primary" | "secondary" | "dark" | "light" | "success" | "error";

export type BoxShadowTheme = "primary";

export interface Shape {
  rounded: string;
  pill: string;
  circle: string;
}

export type ZIndex = "toast" | "modal" | "header" | "menuItem";

export interface Layout {
  sm: string;
  md: string;
  lg: string;
}
