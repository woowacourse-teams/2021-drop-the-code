import { Story, Meta } from "@storybook/react";

import Toast, { Props } from "./Toast";

export default {
  title: "components/Toast",
  component: Toast,
} as Meta;

const Template: Story<Props> = (args) => <Toast {...args} />;

export const Success = Template.bind({});

Success.args = {
  message: "Toast입니다.",
};

export const Error = Template.bind({});

Error.args = {
  message: "Toast입니다.",
  type: "error",
};
