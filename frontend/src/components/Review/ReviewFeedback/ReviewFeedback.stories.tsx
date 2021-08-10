import { Story, Meta } from "@storybook/react";

import { Review } from "types/review";

import ReviewFeedback from "./ReviewFeedback";

export default {
  title: "components/ReviewFeedback",
  component: ReviewFeedback,
} as Meta;

const Template: Story<Pick<Review, "id" | "teacherProfile">> = (args) => <ReviewFeedback {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  id: 2,
  teacherProfile: {
    id: 1,
    name: "파피",
    imageUrl: "https://avatars.githubusercontent.com/u/56301069?v=4",
  },
};
