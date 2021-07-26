import { Story, Meta } from "@storybook/react";

import CareerPicker, { Props } from "components/CareerPicker/CareerPicker";

export default {
  title: "components/CareerPicker",
  component: CareerPicker,
} as Meta;

const Template: Story<Props> = (args) => <CareerPicker {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  filterCareer: 0,
  onSetFilterCareer: () => {
    console.log("Set Career");
  },
};
