import { Language, Reviewer } from "../types/reviewer";

import apiClient from "./apiClient";

export const getLanguageList = () => {
  return apiClient.get<Language[]>(`/languages`);
};

export const getReviewerList = (queryString: string) => {
  return apiClient.get<{ teacherProfiles: Reviewer[]; pageCount: number }>(`/teachers${queryString}`);
};
