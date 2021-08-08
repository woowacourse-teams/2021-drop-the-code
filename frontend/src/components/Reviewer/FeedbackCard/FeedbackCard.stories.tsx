import { Story, Meta } from "@storybook/react";

import { Feedback } from "types/reviewer";

import FeedbackCard from "components/Reviewer/FeedbackCard/FeedbackCard";

export default {
  title: "components/FeedbackCard",
  component: FeedbackCard,
} as Meta;

const Template: Story<Feedback> = (args) => <FeedbackCard {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  id: 1,
  star: 5,
  comment: "리뷰가 구체적이어서 좋았습니다!",
  studentProfile: {
    id: 1,
    name: "파피",
    imageUrl: "https://avatars.githubusercontent.com/u/52202474?v=4",
  },
};
