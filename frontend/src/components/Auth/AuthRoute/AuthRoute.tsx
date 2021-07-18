import { Redirect, Route, RouteProps } from "react-router-dom";

interface Props extends RouteProps {
  isPrivate?: boolean;
  isAuthenticated: boolean;
  redirectTo: string;
}

const PrivateRoute = ({ isAuthenticated, redirectTo, children, ...props }: Omit<Props, "isPrivate">) => (
  <Route {...props}>{isAuthenticated ? children : <Redirect to={redirectTo} />}</Route>
);

const PublicRoute = ({ isAuthenticated, redirectTo, children, ...props }: Omit<Props, "isPrivate">) => (
  <Route {...props}>{isAuthenticated ? <Redirect to={redirectTo} /> : children}</Route>
);

const AuthRoute = ({ isPrivate, ...props }: Props) =>
  isPrivate ? <PrivateRoute {...props} /> : <PublicRoute {...props} />;

export default AuthRoute;
