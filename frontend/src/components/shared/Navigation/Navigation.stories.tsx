import { Story, Meta } from "@storybook/react";

import Navigation, { Props } from "components/shared/Navigation/Navigation";

export default {
  title: "components/shared/Navigation",
  component: Navigation,
} as Meta;

const Template: Story<Props> = (args) => <Navigation {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  leftChildren: <div>로고</div>,
  children: "내용물",
};
