import { Language, Reviewer, ReviewerRegisterFormData } from "types/reviewer";

import apiClient from "apis/apiClient";

export const getLanguageList = () => {
  return apiClient.get<Language[]>(`/languages`);
};

export const getReviewerList = (queryString: string) => {
  return apiClient.get<{ teacherProfiles: Reviewer[]; pageCount: number }>(`/teachers${queryString}`);
};

export const getReviewer = (reviewerId: number) => {
  return apiClient.get<Reviewer>(`/teachers/${reviewerId}`);
};

export const registerReviewer = (reviewerRegisterFormData: ReviewerRegisterFormData) => {
  return apiClient.post(`/teachers`, reviewerRegisterFormData);
};
