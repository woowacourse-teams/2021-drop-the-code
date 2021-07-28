import { Story, Meta } from "@storybook/react";

import { Review } from "types/review";

import ReviewCard from "components/Review/ReviewCard/ReviewCard";

export default {
  title: "components/ReviewCard",
  component: ReviewCard,
} as Meta;

const Template: Story<Review> = (args) => <ReviewCard {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  id: 1,
  title: "타입스크립트 리액트 리뷰",
  content:
    "타입스크립트를 처음써보는데 정말 어렵네요 리액트에서 제공하는 타입들을 적용해보았는데 잘 이루어졌는지 궁금합니다. 타입 정의 위주로 배워보고싶어요!",
  progress: "ON_GOING",
  createdAt: [2021, 7, 22],
};
