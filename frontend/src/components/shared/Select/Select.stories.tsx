import { Story, Meta } from "@storybook/react";

import Select, { Props } from "components/shared/Select/Select";

export default {
  title: "components/shared/Select",
  component: Select,
} as Meta;

const Template: Story<Props> = (args) => <Select {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: (
    <>
      <option value="rec">추천순</option>
      <option value="fir">1</option>
      <option value="sec">2</option>
    </>
  ),
};
