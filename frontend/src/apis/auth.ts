import { User } from "types/auth";

import apiClient from "apis/apiClient";

export const oauthLogin = (queryString: string) => {
  return apiClient.get<User>(`/login/oauth${queryString}`);
};
