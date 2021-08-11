import { ReactNode, useEffect, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";

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

  axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

  const { data } = useQuery(
    QUERY_KEY.CHECK_MEMBER,
    async () => {
      const response = await revalidate(() => checkMember());

      if (!response.isSuccess) {
        removeAccessToken();
        removeRefreshToken();

        return;
      }

      return { ...response.data };
    },
    {
      refetchInterval: false,
      refetchIntervalInBackground: false,
      refetchOnMount: false,
      refetchOnReconnect: false,
      refetchOnWindowFocus: false,
    }
  );

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

        removeAccessToken();
        removeRefreshToken();

        setUser(null);
      }

      return response;
    });
  });

  const logout = () => {
    logoutMutation.mutate();
  };

  useEffect(() => {
    axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
  }, [accessToken]);

  useEffect(() => {
    if (!data) return;
    if (!accessToken || !refreshToken) return;

    login({ ...data, accessToken, refreshToken });
  }, [data]);

  const isAuthenticated = user !== null || accessToken !== null;

  return <AuthContext.Provider value={{ isAuthenticated, user, login, logout }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
