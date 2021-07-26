import { User } from "types/auth";

import apiClient from "apis/apiClient";

export const checkMember = () => {
  return apiClient.get<User>("/members/me");
};

export const oauthLogin = (queryString: string) => {
  return apiClient.get<User>(`/login/oauth${queryString}`);
};
