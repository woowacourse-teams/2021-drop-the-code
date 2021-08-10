import axios from "axios";

import { Response } from "apis/apiClient";
import { renewToken } from "apis/auth";
import useLocalStorage from "hooks/useLocalStorage";
import { LOCAL_STORAGE_KEY } from "utils/constants/key";

const useRevalidate = () => {
  const [_, setAccessToken] = useLocalStorage(LOCAL_STORAGE_KEY.ACCESS_TOKEN, "");
  const [refreshToken] = useLocalStorage(LOCAL_STORAGE_KEY.REFRESH_TOKEN, "");

  const revalidate = async <T>(request: () => Promise<Response<T>>) => {
    const response = await request();

    if (!response.isSuccess) {
      if (response.code === 401) {
        const renewTokenResponse = await renewToken(refreshToken);

        if (!renewTokenResponse.isSuccess) {
          return response;
        }

        const {
          data: { accessToken: renewAccesToken },
        } = renewTokenResponse;

        setAccessToken(renewAccesToken);

        axios.defaults.headers.common.Authorization = `Bearer ${renewAccesToken}`;

        return request();
      }
    }

    return response;
  };

  return {
    revalidate,
  };
};

export default useRevalidate;
