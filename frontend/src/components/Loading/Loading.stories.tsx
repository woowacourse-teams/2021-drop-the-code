import { Story, Meta } from "@storybook/react";

import Loading from "./Loading";

export default {
  title: "components/Loading",
  component: Loading,
} as Meta;

const Template: Story = (args) => <Loading {...args} />;

export const Basic = Template.bind({});
