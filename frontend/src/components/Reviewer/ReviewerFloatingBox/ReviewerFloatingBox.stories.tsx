import { Story, Meta } from "@storybook/react";

import { reviewers } from "__mock__/reviewers";
import ReviewerFloatingBox, { Props } from "components/Reviewer/ReviewerFloatingBox/ReviewerFloatingBox";

export default {
  title: "components/ReviewerFloatingBox",
  component: ReviewerFloatingBox,
} as Meta;

const Template: Story<Props> = (args) => <ReviewerFloatingBox {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  reviewer: reviewers[0],
};
