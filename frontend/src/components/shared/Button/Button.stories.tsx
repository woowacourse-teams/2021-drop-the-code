import { Story, Meta } from "@storybook/react";

import Button, { Props } from "./Button";

export default {
  title: "components/shared/Button",
  component: Button,
} as Meta;

const Template: Story<Props> = (args) => (
  <Button {...args}>
    <a>111</a>
  </Button>
);

export const Basic = Template.bind({});

Basic.args = {
  children: "내용물",
};
