import { Story, Meta } from "@storybook/react";

import ReviewFeedback, { Props } from "./ReviewFeedback";

export default {
  title: "components/ReviewFeedback",
  component: ReviewFeedback,
} as Meta;

const Template: Story<Props> = (args) => <ReviewFeedback {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  reviewId: 2,
  teacherProfile: {
    id: 1,
    name: "파피",
    imageUrl: "https://avatars.githubusercontent.com/u/56301069?v=4",
  },
};
