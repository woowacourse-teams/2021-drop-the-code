import { Story, Meta } from "@storybook/react";

import Textarea, { Props } from "./Textarea";

export default {
  title: "components/shared/Textarea",
  component: Textarea,
} as Meta;

const Template: Story<Props> = (args) => <Textarea {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  placeholder: "리뷰어 소개를 입력해주세요.",
  labelText: "리뷰어 소개",
  errorMessage: "5,000자 이내로 입력해주세요.",
};
