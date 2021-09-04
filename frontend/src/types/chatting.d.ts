export interface ChattingHistory {
  id: number;
  name: string;
  imageUrl: string;
  latestMessage: string;
  createdAt: string;
}

export interface SingleChatting {
  senderId: number;
  senderName: string;
  senderImageUrl: string;
  receiverId: number;
  receiverName: string;
  receiverImageUrl: string;
  message: string;
  createdAt: string;
}
