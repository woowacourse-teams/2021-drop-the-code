import { User } from "types/auth";

import apiClient from "apis/apiClient";

export const checkMember = () => {
  return apiClient.get<User>("/members/me");
};

export const oauthLogin = (queryString: string) => {
  return apiClient.get<User>(`/login/oauth${queryString}`);
};

export const requestLogout = () => {
  return apiClient.post("/logout");
};

export const renewToken = (refreshToken: string) => {
  return apiClient.post<{ accessToken: string }>(
    "/token",
    { refreshToken },
    { headers: { "content-type": "application/x-www-form-urlencoded" } }
  );
};
