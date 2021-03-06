import axios, { AxiosRequestConfig } from "axios";

interface ApiSuccess<T> {
  isSuccess: true;
  data: T;
}

interface ApiFailure<T> {
  isSuccess: false;
  error: T;
  code: number;
}

export type Response<T> = ApiSuccess<T> | ApiFailure<{ errorMessage: string }>;

const apiClient = {
  get: async <T>(url: string, config?: AxiosRequestConfig): Promise<Response<T>> => {
    try {
      const response = await axios.get<T>(url, config);

      return {
        isSuccess: true,
        data: response.data,
      };
    } catch (error: any) {
      return {
        isSuccess: false,
        error: error.response.data,
        code: error.response.status,
      };
    }
  },
  post: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<Response<T>> => {
    try {
      const response = await axios.post<T>(url, data, config);

      return {
        isSuccess: true,
        data: response.data,
      };
    } catch (error: any) {
      return {
        isSuccess: false,
        error: error.response.data,
        code: error.response.status,
      };
    }
  },
  patch: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<Response<T>> => {
    try {
      const response = await axios.patch<T>(url, data, config);

      return {
        isSuccess: true,
        data: response.data,
      };
    } catch (error: any) {
      return {
        isSuccess: false,
        error: error.response.data,
        code: error.response.status,
      };
    }
  },
  put: async <T>(url: string, data?: any, config?: AxiosRequestConfig): Promise<Response<T>> => {
    try {
      const response = await axios.put<T>(url, data, config);

      return {
        isSuccess: true,
        data: response.data,
      };
    } catch (error: any) {
      return {
        isSuccess: false,
        error: error.response.data,
        code: error.response.status,
      };
    }
  },
  delete: async <T>(url: string, config?: AxiosRequestConfig): Promise<Response<T>> => {
    try {
      const response = await axios.delete<T>(url, config);

      return {
        isSuccess: true,
        data: response.data,
      };
    } catch (error: any) {
      return {
        isSuccess: false,
        error: error.response.data,
        code: error.response.status,
      };
    }
  },
} as const;

export default apiClient;
