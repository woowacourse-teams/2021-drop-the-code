import { Review, ReviewListMode, ReviewRequestFormData } from "../types/review";

import apiClient from "./apiClient";

export const requestReview = (reviewRequestFormData: ReviewRequestFormData) => {
  return apiClient.post(`/teachers`, reviewRequestFormData);
};

export const getReviewList = (id: number, mode: ReviewListMode) => {
  return apiClient.get<{ reviews: Review[] }>(`/reviews/${mode}/${id}`);
};
