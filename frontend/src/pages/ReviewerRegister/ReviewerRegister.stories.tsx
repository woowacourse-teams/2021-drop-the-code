import { Story, Meta } from "@storybook/react";

import ReviewerRegister from "pages/ReviewerRegister/ReviewerRegister";

export default {
  title: "pages/ReviewerRegister",
  component: ReviewerRegister,
} as Meta;

const Template: Story = (args) => <ReviewerRegister {...args} />;

export const Basic = Template.bind({});
