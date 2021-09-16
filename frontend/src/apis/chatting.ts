import { ChattingHistory, SingleChatting } from "types/chatting";

import apiClient from "./apiClient";

export const getChattingList = (id: number) => {
  return apiClient.get<ChattingHistory[]>(`/messages/${id}`);
};

export const getChattingConnect = (studentId: number, teacherId: number) => {
  return apiClient.get<{ roomId: number }>(`/rooms?studentId=${studentId}&teacherId=${teacherId}`);
};

export const getSingleChatting = (queryString: string) => {
  return apiClient.get<SingleChatting[]>(`/messages${queryString}`);
};
