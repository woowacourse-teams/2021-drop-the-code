import { Story, Meta } from "@storybook/react";

import Button, { Props } from "components/shared/Button/Button";

export default {
  title: "components/shared/Button",
  component: Button,
} as Meta;

const BasicTemplate: Story<Props> = (args) => <Button {...args} />;

export const Basic = BasicTemplate.bind({});

Basic.args = {
  children: "내용물",
};

export const Disabled = BasicTemplate.bind({});

Disabled.args = {
  children: "내용물",
  disabled: true,
};

const ToggleTemplate: Story<Props> = (args) => (
  <>
    <Button {...args} themeColor="secondary" active={false} />
    <Button {...args} themeColor="secondary" active={true} />
  </>
);

export const Toggle = ToggleTemplate.bind({});

Toggle.args = {
  children: "내용물",
};
