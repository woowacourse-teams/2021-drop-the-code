import { Story, Meta } from "@storybook/react";

import Navigation, { Props } from "./Navigation";

export default {
  title: "components/Navigation",
  component: Navigation,
} as Meta;

const Template: Story<Props> = (args) => <Navigation {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  title: <div>로고</div>,
  children: "내용물",
};
