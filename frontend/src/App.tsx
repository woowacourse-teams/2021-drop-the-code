import { Route, Switch } from "react-router-dom";

import AuthRoute from "components/Auth/AuthRoute/AuthRoute";
import Header from "components/Header/Header";
import useAuthContext from "hooks/useAuthContext";
import NotFound from "pages/NotFound/NotFound";
import { PATH } from "utils/constants/path";
import { ROUTE } from "utils/constants/route";

const App = () => {
  const { isAuthenticated } = useAuthContext();

  return (
    <>
      <Switch>
        {ROUTE.map(({ path, Component, isPrivate, exact }) => (
          <AuthRoute
            isPrivate={isPrivate}
            isAuthenticated={isAuthenticated}
            key={path}
            path={path}
            redirectTo={PATH.MAIN}
            exact={exact}
          >
            <Header />
            <main>
              <Component />
            </main>
          </AuthRoute>
        ))}
        <Route path={PATH.NOT_FOUND}>
          <NotFound />
        </Route>
      </Switch>
    </>
  );
};

export default App;
