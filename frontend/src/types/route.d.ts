import { ReactElement, ReactNode } from "react";

export interface RouteShape {
  path: string;
  Component: () => ReactElement;
  isPrivate: boolean;
  exact: boolean;
}

export interface NavMenuShape {
  to: string;
  children: ReactNode;
  isPrivate: boolean;
}
