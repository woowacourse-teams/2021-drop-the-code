import { ReactNode, useEffect, useState } from "react";
import { useMutation, useQueryClient } from "react-query";

import axios from "axios";
import { User } from "types/auth";

import { checkMember, requestLogout } from "apis/auth";
import { AuthContext } from "hooks/useAuthContext";
import useLocalStorage from "hooks/useLocalStorage";
import useRevalidate from "hooks/useRevalidate";
import { LOCAL_STORAGE_KEY, QUERY_KEY } from "utils/constants/key";

export interface Props {
  children: ReactNode;
}

const AuthProvider = ({ children }: Props) => {
  const [user, setUser] = useState<User | null>(null);
  const [accessToken, setAccessToken, removeAccessToken] = useLocalStorage<string | null>(
    LOCAL_STORAGE_KEY.ACCESS_TOKEN,
    null
  );
  const [refreshToken, setRefreshToken, removeRefreshToken] = useLocalStorage<string | null>(
    LOCAL_STORAGE_KEY.REFRESH_TOKEN,
    null
  );
  const { revalidate } = useRevalidate();
  const queryClient = useQueryClient();

  const authCheck = async () => {
    const response = await revalidate(() => checkMember());

    if (!response.isSuccess) {
      removeAccessToken();
      removeRefreshToken();

      return;
    }

    if (!accessToken || !refreshToken) return;

    login({ ...response.data, accessToken, refreshToken });
  };

  const login = (user: User) => {
    const { accessToken, refreshToken } = user;

    setAccessToken(accessToken);
    setRefreshToken(refreshToken);

    setUser(user);
  };

  const logoutMutation = useMutation(() => {
    return revalidate(async () => {
      const response = await requestLogout();

      if (response.isSuccess) {
        queryClient.invalidateQueries(QUERY_KEY.OAUTH_LOGIN);

        setUser(null);
      }

      removeAccessToken();
      removeRefreshToken();

      return response;
    });
  });

  const logout = () => {
    logoutMutation.mutate();
  };

  useEffect(() => {
    if (!accessToken) return;
    axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

    authCheck();
  }, [accessToken]);

  const isAuthenticated = user !== null || accessToken !== null;

  return <AuthContext.Provider value={{ isAuthenticated, user, login, logout }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
