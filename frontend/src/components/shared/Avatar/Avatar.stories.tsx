import { Story, Meta } from "@storybook/react";

import Avatar, { Props } from "./Avatar";

export default {
  title: "components/shared/Avatar",
  component: Avatar,
} as Meta;

const Template: Story<Props> = (args) => <Avatar {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  width: "100px",
  height: "100px",
  imageUrl: "https://avatars.githubusercontent.com/u/52202474?v=4",
  alt: "리뷰어 프로필 이미지",
};
