import { Review, Role, ReviewRequestFormData, ReviewFeedback } from "types/review";

import apiClient from "apis/apiClient";

export const requestReview = (reviewRequestFormData: ReviewRequestFormData) => {
  return apiClient.post(`/reviews`, reviewRequestFormData);
};

export const getReview = (id: number) => {
  return apiClient.get<Review>(`/reviews/${id}`);
};

export const getReviewList = (id: number, mode: Role, queryString: string) => {
  return apiClient.get<{ reviews: Review[]; pageCount: number }>(`/reviews/${mode.toLowerCase()}/${id}${queryString}`);
};

export const cancelReview = (id: number) => {
  return apiClient.delete(`/reviews/${id}`);
};

export const denyReview = (id: number) => {
  return apiClient.patch(`/reviews/${id}/deny`);
};

export const acceptReview = (id: number) => {
  return apiClient.patch(`/reviews/${id}/accept`);
};

export const editReview = (id: number, reviewRequestFormData: ReviewRequestFormData) => {
  return apiClient.patch(`/reviews/${id}`, reviewRequestFormData);
};

export const completeReview = (id: number) => {
  return apiClient.patch(`/reviews/${id}/complete`);
};

export const finishReview = (id: number, reviewFeedback: ReviewFeedback) => {
  return apiClient.patch(`/reviews/${id}/finish`, reviewFeedback);
};
