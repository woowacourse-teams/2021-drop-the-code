import { User } from "types/auth";

import apiClient from "apis/apiClient";

export const checkMember = () => {
  return apiClient.get<Omit<User, "accessToken" | "refreshToken">>("/members/me");
};

export const oauthLogin = (queryString: string) => {
  return apiClient.get<User>(`/login/oauth${queryString}`);
};

export const requestLogout = () => {
  return apiClient.post("/logout");
};

export const renewToken = (refreshToken: string) => {
  const params = new URLSearchParams();
  params.append("refreshToken", refreshToken);

  return apiClient.post<{ accessToken: string }>("/token", params, {
    headers: { "content-type": "application/x-www-form-urlencoded" },
  });
};
