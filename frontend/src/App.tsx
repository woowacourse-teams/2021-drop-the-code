import { BrowserRouter, Switch, Route, NavLink, Link } from "react-router-dom";

import { ThemeProvider } from "styled-components";

import Logo from "./assets/logo.svg";
import PrivateRoute from "./components/AuthRoute/PrivateRoute";
import GlobalStyle from "./components/GlobalStyle/GlobalStyle";
import Navigation from "./components/Navigation/Navigation";
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
          title={
            <h1>
              <Link to={PATH.MAIN}>
                <FlexCenter>
                  <Logo width={30} height={30} />
                  <p>코드봐줘</p>
                </FlexCenter>
              </Link>
            </h1>
          }
        >
          <>
            {NAV_MENU.filter(({ isPrivate }) => isPrivate === isAuthenticated).map(({ to, children }) => (
              <NavLink key={to} to={to}>
                {children}
              </NavLink>
            ))}
            {!isAuthenticated && <button>로그인</button>}
            {isAuthenticated && <button>로그아웃</button>}
          </>
        </Navigation>
        <Switch>
          {ROUTE.map(({ path, Component, isPrivate }) =>
            isPrivate ? (
              <PrivateRoute isAuthenticated={isAuthenticated} key={path} redirectTo={PATH.MAIN}>
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
