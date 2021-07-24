import { Review, Role, ReviewRequestFormData } from "../types/review";

import apiClient from "./apiClient";

export const requestReview = (reviewRequestFormData: ReviewRequestFormData) => {
  return apiClient.post(`/teachers`, reviewRequestFormData);
};

export const getReview = (id: number) => {
  return apiClient.get<Review>(`/reviews/${id}`);
};

export const getReviewList = (id: number, mode: Role) => {
  return apiClient.get<{ reviews: Review[] }>(`/reviews/${mode}/${id}`);
};

export const patchReviewProgress = (id: number) => {
  return apiClient.patch<{ reviews: Review[] }>(`/reviews/${id}`);
};
