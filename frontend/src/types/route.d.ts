export interface RouteShape {
  path: string;
  Component: () => JSX.Element;
  isPrivate: boolean;
}
