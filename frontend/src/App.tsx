import { Switch } from "react-router-dom";

import axios from "axios";

import AuthRoute from "./components/Auth/AuthRoute/AuthRoute";
import Header from "./components/Header/Header";
import useAuthContext from "./hooks/useAuthContext";
import { PATH, ROUTE } from "./utils/constants/route";

axios.defaults.baseURL = process.env.SERVER_BASE_URL;

const App = () => {
  const { user } = useAuthContext();

  return (
    <>
      <Header />
      <Switch>
        {ROUTE.map(({ path, Component, isPrivate, exact }) => (
          <AuthRoute
            isPrivate={isPrivate}
            isAuthenticated={!!user}
            key={path}
            path={path}
            redirectTo={PATH.MAIN}
            exact={exact}
          >
            <Component />
          </AuthRoute>
        ))}
      </Switch>
    </>
  );
};

export default App;
