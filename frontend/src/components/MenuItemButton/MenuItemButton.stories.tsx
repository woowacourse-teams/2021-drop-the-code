import { Story, Meta } from "@storybook/react";

import Button from "../shared/Button/Button";

import MenuItemButton, { Props } from "./MenuItemButton";

export default {
  title: "components/MenuItemButton",
  component: MenuItemButton,
} as Meta;

const Template: Story<Props> = (args) => <MenuItemButton {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: "연차",
  // eslint-disable-next-line react/display-name
  contents: (close) => (
    <div>
      <label htmlFor="career">
        연차
        <input id="career" />
      </label>
      <Button
        onClick={() => {
          close();
        }}
      >
        저장
      </Button>
    </div>
  ),
};
