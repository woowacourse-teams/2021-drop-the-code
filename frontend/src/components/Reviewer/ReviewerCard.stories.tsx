import { Story, Meta } from "@storybook/react";

import { Reviewer } from "../../types/reviewer";

import ReviewerCard from "./ReviewerCard";

export default {
  title: "components/ReviewerCard",
  component: ReviewerCard,
} as Meta;

const Template: Story<Reviewer> = (args) => <ReviewerCard {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  id: 1,
  imageUrl: "https://avatars.githubusercontent.com/u/52202474?v=4",
  career: 3,
  reviewCount: 300,
  averageResponseTime: 1,
  title: "안녕하세요~",
  techSpec: [{ language: "JavaScript", skills: ["React", "Angular"] }],
};
