import { Story, Meta } from "@storybook/react";

import { COLOR } from "../../utils/constants/color";

import Chip, { Props } from "./Chip";

export default {
  title: "components/Chip",
  component: Chip,
} as Meta;

const Template: Story<Props> = (args) => <Chip {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: <p>3년 이내 경력</p>,
  color: COLOR.WHITE,
  backgroundColor: COLOR.BLACK,
};
