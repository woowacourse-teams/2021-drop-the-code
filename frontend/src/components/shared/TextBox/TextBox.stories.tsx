import { PropsWithChildren } from "react";

import { Story, Meta } from "@storybook/react";

import TextBox, { Props } from "./TextBox";

export default {
  title: "components/shared/TextBox",
  component: TextBox,
} as Meta;

const Template: Story<PropsWithChildren<Props>> = (args) => (
  <div css={{ width: "18.75rem" }}>
    <TextBox {...args} />
  </div>
);

export const Basic = Template.bind({});

const value = "안녕하세요. 서지환입니다 \n\n dddd  ddd \ndsdf \n\n제가 개발 제일 잘해요.\n\n";

Basic.args = {
  titleChildren: "네이버 프론트엔드 개발자 서지환입니다.",
  children: value,
};
