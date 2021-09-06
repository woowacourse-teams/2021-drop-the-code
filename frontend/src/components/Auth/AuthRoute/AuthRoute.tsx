import { useEffect } from "react";
import { Redirect, Route, RouteProps } from "react-router-dom";

import useAuthContext from "hooks/useAuthContext";
import useToastContext from "hooks/useToastContext";
import { ERROR_MESSAGE } from "utils/constants/message";

interface Props extends RouteProps {
  isPrivate?: boolean;
  redirectTo: string;
}

const AuthRoute = ({ isPrivate, children, redirectTo, ...props }: Props) => {
  const toast = useToastContext();
  const { isAuthenticated } = useAuthContext();

  useEffect(() => {
    if (isPrivate && !isAuthenticated) {
      toast(ERROR_MESSAGE.AUTH.REQUIRED, { type: "error" });
    }
  }, []);

  return isPrivate ? (
    <Route {...props}>{isAuthenticated ? children : <Redirect to={redirectTo} />}</Route>
  ) : (
    <Route {...props}>{children}</Route>
  );
};

export default AuthRoute;
