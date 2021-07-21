import { Redirect, Route, RouteProps } from "react-router-dom";

interface Props extends RouteProps {
  isPrivate?: boolean;
  isAuthenticated: boolean;
  redirectTo: string;
}

const PrivateRoute = ({ isAuthenticated, redirectTo, children, ...props }: Omit<Props, "isPrivate">) => (
  <Route {...props}>{isAuthenticated ? children : <Redirect to={redirectTo} />}</Route>
);

const AuthRoute = ({ isPrivate, children, redirectTo, ...props }: Props) => {
  return isPrivate ? (
    <PrivateRoute redirectTo={redirectTo} {...props}>
      {children}
    </PrivateRoute>
  ) : (
    <Route {...props}>{children}</Route>
  );
};

export default AuthRoute;
