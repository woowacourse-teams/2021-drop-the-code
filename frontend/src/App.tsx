import { BrowserRouter, Switch, Route } from "react-router-dom";

import { ThemeProvider } from "styled-components";

import PrivateRoute from "./components/AuthRoute/PrivateRoute";
import GlobalStyle from "./components/GlobalStyle/GlobalStyle";
import { PATH, ROUTE } from "./utils/constants/route";
import { THEME } from "./utils/constants/theme";

const App = () => {
  return (
    <ThemeProvider theme={THEME}>
      <GlobalStyle />
      <BrowserRouter>
        <Switch>
          {ROUTE.map(({ path, Component, isPrivate }) =>
            isPrivate ? (
              <PrivateRoute isAuthenticated={false} key={path} redirectTo={PATH.MAIN}>
                <Component />
              </PrivateRoute>
            ) : (
              <Route key={path} path={path}>
                <Component />
              </Route>
            )
          )}
        </Switch>
      </BrowserRouter>
    </ThemeProvider>
  );
};

export default App;
