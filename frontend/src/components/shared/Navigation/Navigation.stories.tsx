import { Story, Meta } from "@storybook/react";

import Navigation, { Props } from "./Navigation";

export default {
  title: "components/shared/Navigation",
  component: Navigation,
} as Meta;

const Template: Story<Props> = (args) => <Navigation {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: <div>로고</div>,
  rightChildren: "내용물",
};
