import { Story, Meta } from "@storybook/react";

import TextBox, { Props } from "./TextBox";

export default {
  title: "components/TextBox",
  component: TextBox,
} as Meta;

const Template: Story<Props> = (args) => (
  <div css={{ width: "300px" }}>
    <TextBox {...args} />
  </div>
);

export const Basic = Template.bind({});

const value = "안녕하세요. 서지환입니다 \n\n dddd  ddd \ndsdf \n\n제가 개발 제일 잘해요.\n\n";

Basic.args = {
  title: "네이버 프론트엔드 개발자 서지환입니다.",
  description: value,
};
