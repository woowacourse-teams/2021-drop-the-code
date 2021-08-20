import { Suspense } from "react";
import { QueryClient, QueryClientProvider } from "react-query";
import { Route, Switch } from "react-router-dom";

import axios from "axios";
import { ThemeProvider } from "styled-components";

import AuthProvider from "components/Auth/AuthProvider/AuthProvider";
import AuthRoute from "components/Auth/AuthRoute/AuthRoute";
import ErrorBoundary from "components/Error/ErrorBoundary";
import GlobalStyle from "components/GlobalStyle/GlobalStyle";
import Header from "components/Header/Header";
import Loading from "components/Loading/Loading";
import ModalProvider from "components/ModalProvider/ModalProvider";
import ToastProvider from "components/ToastProvider/ToastProvider";
import NotFound from "pages/NotFound/NotFound";
import { THEME } from "theme/theme";
import { PATH } from "utils/constants/path";
import { ROUTE } from "utils/constants/route";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
    },
  },
});

axios.defaults.baseURL = process.env.SERVER_BASE_URL;

const App = () => (
  <ThemeProvider theme={THEME}>
    <ErrorBoundary>
      <ToastProvider>
        <QueryClientProvider client={queryClient}>
          <Suspense fallback={<Loading />}>
            <AuthProvider>
              <ModalProvider>
                <GlobalStyle />
                <Suspense fallback={<Loading />}>
                  <Switch>
                    {ROUTE.map(({ path, Component, isPrivate, exact }) => (
                      <AuthRoute isPrivate={isPrivate} key={path} path={path} redirectTo={PATH.MAIN} exact={exact}>
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
                </Suspense>
              </ModalProvider>
            </AuthProvider>
          </Suspense>
        </QueryClientProvider>
      </ToastProvider>
    </ErrorBoundary>
  </ThemeProvider>
);

export default App;
