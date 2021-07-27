import { ReviewRequestFormData } from "../types/review";

import apiClient from "./apiClient";

export const requestReview = (reviewRequestFormData: ReviewRequestFormData) => {
  return apiClient.post(`/teachers`, reviewRequestFormData);
};
