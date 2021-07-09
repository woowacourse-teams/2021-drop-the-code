import { Story, Meta } from "@storybook/react";

import Input, { Props } from "./Input";

export default {
  title: "components/shared/Input",
  component: Input,
} as Meta;

const Template: Story<Props> = (args) => <Input {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  placeHolder: "타이틀을 입력해주세요.",
  labelText: "타이틀",
  errorMessage: "30자 이내로 작성해주세요.",
};
