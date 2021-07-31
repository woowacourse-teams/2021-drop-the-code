import { Review } from "types/review";

export const reviews: Review[] = [...Array(10)].map((_, index) => ({
  id: index + 1,
  title: "타입스크립트 리액트 리뷰",
  content:
    "타입스크립트를 처음써보는데 정말 어렵네요 리액트에서 제공하는 타입들을 적용해보았는데 잘 이루어졌는지 궁금합니다. 타입 정의 위주로 배워보고싶어요!",
  progress: "ON_GOING",
  prUrl: "https://github.com",
  teacherProfile: {
    id: 1,
    name: "신세희",
    imageUrl: "https://avatars.githubusercontent.com/u/52202474?v=4",
  },
  studentProfile: {
    id: 2,
    name: "서지환",
    imageUrl: "https://avatars.githubusercontent.com/u/52202474?v=4",
  },
  createdAt: [2021, 8, 1],
}));
