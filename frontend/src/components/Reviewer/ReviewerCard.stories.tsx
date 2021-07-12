import { Story, Meta } from "@storybook/react";

import ReviewerCard, { Props } from "./ReviewerCard";

export default {
  title: "components/ReviewerCard",
  component: ReviewerCard,
} as Meta;

const Template: Story<Props> = (args) => <ReviewerCard {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  id: 1,
  avatarUrl: "https://avatars.githubusercontent.com/u/52202474?v=4",
  career: 3,
  reviewCount: 300,
  averageResponseTime: 1,
  title: "안녕하세요~",
  languages: ["JavaScript", "Java"],
  skills: ["React", "Angular"],
};
