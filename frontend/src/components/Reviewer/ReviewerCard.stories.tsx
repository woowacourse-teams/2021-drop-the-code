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
  sumReviewCount: 300,
  averageReviewTime: 1,
  title: "안녕하세요~",
  techSpec: {
    languages: [
      {
        id: 1,
        name: "java",
      },
      {
        id: 2,
        name: "javascript",
      },
    ],
    skills: [
      {
        id: 1,
        name: "spring",
      },
      {
        id: 2,
        name: "react",
      },
    ],
  },
};
