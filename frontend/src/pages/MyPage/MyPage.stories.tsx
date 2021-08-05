import { Story, Meta } from "@storybook/react";

import MyPage from "pages/MyPage/MyPage";

export default {
  title: "pages/MyPage",
  component: MyPage,
} as Meta;

const Template: Story = (args) => <MyPage {...args} />;

export const Basic = Template.bind({});
