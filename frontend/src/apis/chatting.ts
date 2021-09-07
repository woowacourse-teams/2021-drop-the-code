import { ChattingHistory, SingleChatting } from "types/chatting";

import apiClient from "./apiClient";

export const getChattingList = (id: number) => {
  return apiClient.get<ChattingHistory[]>(`/messages/${id}`);
};

export const getChatting = (roomId: number) => {
  return apiClient.get<SingleChatting[]>(`/messages/${roomId}`);
};
