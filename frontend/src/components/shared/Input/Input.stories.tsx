import { Story, Meta } from "@storybook/react";

import Input, { Props } from "components/shared/Input/Input";

export default {
  title: "components/shared/Input",
  component: Input,
} as Meta;

const Template: Story<Props> = (args) => <Input {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  placeholder: "타이틀을 입력해주세요.",
  labelText: "타이틀",
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  onChange: () => {},
};

export const Error = Template.bind({});

Error.args = {
  placeholder: "타이틀을 입력해주세요.",
  labelText: "타이틀",
  errorMessage: "5자 이내로 작성해주세요.",
  value: "올바르지 않은 입력",
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  onChange: () => {},
};

export const Valid = Template.bind({});

Valid.args = {
  placeholder: "타이틀을 입력해주세요.",
  labelText: "타이틀",
  value: "올바른 입력",
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  onChange: () => {},
};
