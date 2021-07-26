import { ReactNode, useEffect, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";

import axios from "axios";
import { User } from "types/auth";

import { checkMember, requestLogout } from "apis/auth";
import { AuthContext } from "hooks/useAuthContext";
import useLocalStorage from "hooks/useLocalStorage";

export interface Props {
  children: ReactNode;
}

const AuthProvider = ({ children }: Props) => {
  const [user, setUser] = useState<User | null>(null);
  const [accessToken, setAccessToken, removeAccessToken] = useLocalStorage("accessToken", "");
  const [refreshToken, setRefreshToken, removeRefreshToken] = useLocalStorage("refreshToken", "");

  const queryClinet = useQueryClient();
  const logoutMutation = useMutation(requestLogout, {
    onSuccess: () => {
      queryClinet.invalidateQueries("oauthLogin");

      removeAccessToken();
      removeRefreshToken();

      setUser(null);
    },
  });

  const { data } = useQuery(
    "checkMember",
    async () => {
      axios.defaults.headers.Authorization = `Bearer ${accessToken}`;
      const response = await checkMember();

      if (!response.isSuccess) {
        // TODO: 스낵바

        return;
      }

      return { ...response.data, accessToken, refreshToken };
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
    axios.defaults.headers.Authorization = `Bearer ${accessToken}`;

    setAccessToken(accessToken);
    setRefreshToken(refreshToken);

    setUser(user);
  };

  const logout = () => {
    logoutMutation.mutate();
  };

  useEffect(() => {
    if (!data) return;

    login(data);
  }, []);

  return <AuthContext.Provider value={{ user: user, login, logout }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
