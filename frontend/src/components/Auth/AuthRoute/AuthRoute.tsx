import { Redirect, Route, RouteProps } from "react-router-dom";

import useToastContext from "hooks/useToastContext";
import { ERROR_MESSAGE } from "utils/constants/message";

interface Props extends RouteProps {
  isPrivate?: boolean;
  isAuthenticated: boolean;
  redirectTo: string;
}

const PrivateRoute = ({ isAuthenticated, redirectTo, children, ...props }: Omit<Props, "isPrivate">) => (
  <Route {...props}>{isAuthenticated ? children : <Redirect to={redirectTo} />}</Route>
);

const AuthRoute = ({ isPrivate, children, redirectTo, isAuthenticated, ...props }: Props) => {
  const toast = useToastContext();

  if (isPrivate && !isAuthenticated) {
    toast(ERROR_MESSAGE.AUTH.REQUIRED);
  }

  return isPrivate ? (
    <PrivateRoute redirectTo={redirectTo} isAuthenticated={isAuthenticated} {...props}>
      {children}
    </PrivateRoute>
  ) : (
    <Route {...props}>{children}</Route>
  );
};

export default AuthRoute;
