import { LazyExoticComponent, ReactNode } from "react";

export interface RouteShape {
  path: string;
  Component: LazyExoticComponent;
  isPrivate: boolean;
  exact: boolean;
}

export interface RoleNavMenuShape {
  to: string;
  children: ReactNode;
  isTeacher: boolean;
}

export interface NavMenuShape {
  to: string;
  children: ReactNode;
  isPrivate: boolean;
}
