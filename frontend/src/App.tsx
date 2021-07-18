import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter, Switch, Route, NavLink, Link } from "react-router-dom";

import axios from "axios";
import { ThemeProvider } from "styled-components";

import Logo from "./assets/logo.svg";
import PrivateRoute from "./components/AuthRoute/PrivateRoute";
import GlobalStyle from "./components/GlobalStyle/GlobalStyle";
import Navigation from "./components/Navigation/Navigation";
import Button from "./components/shared/Button/Button";
import { FlexCenter } from "./components/shared/Flexbox/Flexbox";
import { NAV_MENU, PATH, ROUTE } from "./utils/constants/route";
import { THEME } from "./utils/constants/theme";

axios.defaults.baseURL = process.env.SERVER_BASE_URL;

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
    },
  },
});

const App = () => {
  const isAuthenticated = false;

  return (
    <ThemeProvider theme={THEME}>
      <QueryClientProvider client={queryClient}>
        <GlobalStyle />
        <BrowserRouter>
          <Navigation
            rightChildren={
              <>
                {NAV_MENU.filter(({ isPrivate }) => isPrivate === isAuthenticated).map(({ to, children }) => (
                  <NavLink key={to} to={to}>
                    {children}
                  </NavLink>
                ))}
                {!isAuthenticated && (
                  <Button themeColor="secondary" hover={false} css={{ fontWeight: 900 }}>
                    로그인
                  </Button>
                )}
                {isAuthenticated && (
                  <Button themeColor="secondary" hover={false} css={{ fontWeight: 900 }}>
                    로그아웃
                  </Button>
                )}
              </>
            }
          >
            <h1>
              <Link to={PATH.MAIN}>
                <FlexCenter>
                  <Logo width={200} height={50} />
                </FlexCenter>
              </Link>
            </h1>
          </Navigation>
          <Switch>
            {ROUTE.map(({ path, Component, isPrivate, exact }) =>
              isPrivate ? (
                <PrivateRoute
                  isAuthenticated={isAuthenticated}
                  key={path}
                  path={path}
                  redirectTo={PATH.MAIN}
                  exact={exact}
                >
                  <Component />
                </PrivateRoute>
              ) : (
                <Route key={path} path={path} exact={exact}>
                  <Component />
                </Route>
              )
            )}
          </Switch>
        </BrowserRouter>
      </QueryClientProvider>
    </ThemeProvider>
  );
};

export default App;
