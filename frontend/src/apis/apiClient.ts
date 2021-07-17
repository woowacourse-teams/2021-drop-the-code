import axios, { AxiosRequestConfig } from "axios";

interface ApiSuccess<T> {
  isSuccess: true;
  data: T;
}

interface ApiFaillure<T> {
  isSuccess: false;
  error: T;
  code: string;
}

type Response<T> = ApiSuccess<T> | ApiFaillure<{ message: string }>;

const apiClient = {
  get: async <T>(url: string, config?: AxiosRequestConfig): Promise<Response<T>> => {
    try {
      const { data } = await axios.get<T>(url, config);

      return {
        isSuccess: true,
        data,
      };
    } catch (error) {
      return {
        isSuccess: false,
        error: error.response.data,
        code: error.code,
      };
    }
  },
} as const;

export default apiClient;
