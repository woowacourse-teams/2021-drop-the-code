/* eslint-disable @typescript-eslint/no-empty-function */
import { Story, Meta } from "@storybook/react";

import Confirm, { Props } from "./Confirm";

export default {
  title: "components/Confirm",
  component: Confirm,
} as Meta;

const Template: Story<Props> = (args) => <Confirm {...args} />;
export const Basic = Template.bind({});

Basic.args = {
  title: "title",
  onConfirm: () => {},
  onReject: () => {},
};
