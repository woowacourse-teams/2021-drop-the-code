import { Story, Meta } from "@storybook/react";

import ReviewDetail from "./ReviewDetail";

export default {
  title: "pages/ReviewDetail",
  component: ReviewDetail,
} as Meta;

const Template: Story = (args) => <ReviewDetail {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: <div>로고</div>,
  rightChildren: "내용물",
};
