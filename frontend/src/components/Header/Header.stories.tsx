import { Story, Meta } from "@storybook/react";

import Header, { Props } from "./Header";

export default {
  title: "components/Header",
  component: Header,
} as Meta;

const Template: Story<Props> = (args) => <Header {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  title: <div>로고</div>,
  children: "내용물",
};
