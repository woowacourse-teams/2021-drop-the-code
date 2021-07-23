import { Story, Meta } from "@storybook/react";

import { reviewers } from "../../../__mock__/reviewers";

import ReviewerContentBox, { Props } from "./ReviewerContentBox";

export default {
  title: "components/ReviewerContentBox",
  component: ReviewerContentBox,
} as Meta;

const Template: Story<Props> = (args) => <ReviewerContentBox {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  reviewer: reviewers[0],
};
