import { BrowserRouter, Switch, Route, NavLink, Link } from "react-router-dom";

import { ThemeProvider } from "styled-components";

import Logo from "./assets/logo.svg";
import PrivateRoute from "./components/AuthRoute/PrivateRoute";
import GlobalStyle from "./components/GlobalStyle/GlobalStyle";
import Navigation from "./components/Navigation/Navigation";
import Button from "./components/shared/Button/Button";
import { FlexCenter } from "./components/shared/Flexbox/Flexbox";
import { NAV_MENU, PATH, ROUTE } from "./utils/constants/route";
import { THEME } from "./utils/constants/theme";

const App = () => {
  const isAuthenticated = true;

  return (
    <ThemeProvider theme={THEME}>
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
                <Button themeColor="transParent" hover={false}>
                  로그인
                </Button>
              )}
              {isAuthenticated && (
                <Button themeColor="transParent" hover={false}>
                  로그아웃
                </Button>
              )}
            </>
          }
        >
          <h1>
            <Link to={PATH.MAIN}>
              <FlexCenter>
                <Logo width={115} height={30} />
              </FlexCenter>
            </Link>
          </h1>
        </Navigation>
        <Switch>
          {ROUTE.map(({ path, Component, isPrivate }) =>
            isPrivate ? (
              <PrivateRoute isAuthenticated={isAuthenticated} key={path} redirectTo={PATH.MAIN}>
                <Component />
              </PrivateRoute>
            ) : (
              <Route key={path} path={path} exact>
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
