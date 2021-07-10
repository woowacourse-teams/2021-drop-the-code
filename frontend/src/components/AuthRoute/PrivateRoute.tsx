import { Redirect, Route, RouteProps } from "react-router-dom";

interface Props extends RouteProps {
  isAuthenticated: boolean;
  redirectTo: string;
}

const PrivateRoute = ({ isAuthenticated, redirectTo, children, ...props }: Props) => (
  <Route {...props}>{isAuthenticated ? children : <Redirect to={redirectTo} />}</Route>
);

export default PrivateRoute;
