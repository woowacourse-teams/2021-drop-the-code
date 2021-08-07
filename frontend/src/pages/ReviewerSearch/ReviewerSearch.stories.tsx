import { Story, Meta } from "@storybook/react";

import ReviewerSearch from "pages/ReviewerSearch/ReviewerSearch";

export default {
  title: "pages/ReviewerSearch",
  component: ReviewerSearch,
} as Meta;

const Template: Story = (args) => <ReviewerSearch {...args} />;

export const Basic = Template.bind({});
