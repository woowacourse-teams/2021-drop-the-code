import { Story, Meta } from "@storybook/react";

import ReviewerDetail from "./ReviewerDetail";

export default {
  title: "pages/ReviewerDetail",
  component: ReviewerDetail,
} as Meta;

const Template: Story = (args) => <ReviewerDetail {...args} />;

export const Basic = Template.bind({});
