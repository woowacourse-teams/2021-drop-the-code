import { Review, Role, ReviewRequestFormData, Progress } from "types/review";

import apiClient from "apis/apiClient";

export const requestReview = (reviewRequestFormData: ReviewRequestFormData) => {
  return apiClient.post(`/reviews`, reviewRequestFormData);
};

export const getReview = (id: number) => {
  return apiClient.get<Review>(`/reviews/${id}`);
};

export const getReviewList = (id: number, mode: Role) => {
  return apiClient.get<{ reviews: Review[] }>(`/reviews/${mode.toLowerCase()}/${id}`);
};

export const patchReviewProgress = (id: number, progress: Progress) => {
  return apiClient.patch<{ reviews: Review[] }>(`/reviews/${id}${progress.toLowerCase()}`);
};
