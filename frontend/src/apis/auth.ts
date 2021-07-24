import apiClient from "apis/apiClient";

export const oauthLogin = (queryString: string) => {
  return apiClient.get<{ name: string; email: string; imageUrl: string; accessToken: string }>(
    `/login/oauth${queryString}`
  );
};
