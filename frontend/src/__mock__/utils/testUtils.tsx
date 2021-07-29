import React, { ReactElement, Suspense } from "react";
import { QueryClient, QueryClientProvider } from "react-query";
import { BrowserRouter } from "react-router-dom";

import { render, RenderOptions } from "@testing-library/react";
import { ThemeProvider } from "styled-components";

import AuthProvider from "components/Auth/AuthProvider/AuthProvider";
import ErrorBoundary from "components/Error/ErrorBoundary";
import GlobalStyle from "components/GlobalStyle/GlobalStyle";
import Loading from "components/Loading/Loading";
import ModalProvider from "components/ModalProvider/ModalProvider";
import { THEME } from "utils/constants/theme";

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      suspense: true,
      retry: false,
    },
  },
});

interface Props {
  children?: React.ReactNode;
}

const AllTheProviders = ({ children }: Props) => {
  return (
    <ThemeProvider theme={THEME}>
      <QueryClientProvider client={queryClient}>
        <Suspense fallback={<Loading />}>
          <AuthProvider>
            <ModalProvider>
              <GlobalStyle />
              <BrowserRouter>
                <ErrorBoundary>{children}</ErrorBoundary>
              </BrowserRouter>
            </ModalProvider>
          </AuthProvider>
        </Suspense>
      </QueryClientProvider>
    </ThemeProvider>
  );
};

const customRender = (ui: ReactElement, options?: Omit<RenderOptions, "wrapper">) => {
  return render(ui, {
    wrapper: AllTheProviders,
    ...options,
  });
};

export * from "@testing-library/react";

export { customRender as render };
