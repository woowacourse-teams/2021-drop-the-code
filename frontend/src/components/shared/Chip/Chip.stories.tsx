import { Story, Meta } from "@storybook/react";

import Chip, { Props } from "components/shared/Chip/Chip";
import { COLOR } from "utils/constants/color";

export default {
  title: "components/shared/Chip",
  component: Chip,
} as Meta;

const Template: Story<Props> = (args) => <Chip {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: <p>3년 이내 경력</p>,
  color: COLOR.WHITE,
};
