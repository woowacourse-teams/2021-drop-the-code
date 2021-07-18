import apiClient from "./apiClient";

export const oauthLogin = (code: string, providerName: string) => {
  return apiClient.post<{ name: string; email: string; imageUrl: string; accessToken: string }>("/login/oauth", {
    method: "post",
    data: { code, providerName },
  });
};
