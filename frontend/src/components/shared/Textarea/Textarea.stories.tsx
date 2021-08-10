import { Story, Meta } from "@storybook/react";

import Textarea, { Props } from "components/shared/Textarea/Textarea";

export default {
  title: "components/shared/Textarea",
  component: Textarea,
} as Meta;

const Template: Story<Props> = (args) => <Textarea {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  placeholder: "리뷰어 소개를 입력해주세요.",
  labelText: "리뷰어 소개",
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  onChange: () => {},
};

export const Error = Template.bind({});

Error.args = {
  placeholder: "리뷰어 소개를 입력해주세요.",
  labelText: "리뷰어 소개",
  errorMessage: "10자 이내로 입력해주세요.",
  value: "안녕하세요 리뷰어 신세희입니다!",
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  onChange: () => {},
};

export const Valid = Template.bind({});

Valid.args = {
  placeholder: "리뷰어 소개를 입력해주세요.",
  labelText: "리뷰어 소개",
  value: "안녕하세요 리뷰어 신세희입니다!",
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  onChange: () => {},
};
