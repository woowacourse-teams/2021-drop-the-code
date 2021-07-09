import { Story, Meta } from "@storybook/react";

import Button, { Props } from "./Button";

export default {
  title: "components/shared/Button",
  component: Button,
} as Meta;

const BasicTemplate: Story<Props> = (args) => <Button {...args} />;

export const Basic = BasicTemplate.bind({});

Basic.args = {
  children: "내용물",
};

const ToggleTemplate: Story<Props> = (args) => (
  <>
    <Button {...args} themeColor={"white"} active={false} />
    <Button {...args} themeColor={"white"} active={true} />
  </>
);

export const Toggle = ToggleTemplate.bind({});

Toggle.args = {
  children: "내용물",
};
