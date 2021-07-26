import axios from "axios";

import { Response } from "apis/apiClient";
import { renewToken } from "apis/auth";

import useLocalStorage from "./useLocalStorage";

const useRevalidate = () => {
  const [accessToken] = useLocalStorage("accessToken", "");

  const revalidate = async <T>(request: () => Promise<Response<T>>) => {
    const response = await request();

    if (!response.isSuccess) {
      if (response.code === 401) {
        const renewTokenResponse = await renewToken(accessToken);

        if (!renewTokenResponse.isSuccess) {
          return response;
        }

        const {
          data: { accessToken: renewAccesToken },
        } = renewTokenResponse;

        axios.defaults.headers.authorization = `Bearer ${renewAccesToken}`;

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
