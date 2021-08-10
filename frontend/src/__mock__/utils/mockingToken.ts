import { LOCAL_STORAGE_KEY } from "utils/constants/key";

export const mockingToken = () => {
  localStorage.setItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN, JSON.stringify("accessToken"));
  localStorage.setItem(LOCAL_STORAGE_KEY.REFRESH_TOKEN, JSON.stringify("refreshToken"));
};

export const clearToken = () => {
  localStorage.removeItem(LOCAL_STORAGE_KEY.ACCESS_TOKEN);
  localStorage.removeItem(LOCAL_STORAGE_KEY.REFRESH_TOKEN);
};
