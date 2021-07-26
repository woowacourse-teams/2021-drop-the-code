import { Story, Meta } from "@storybook/react";

import History from "pages/History/History";

export default {
  title: "pages/History",
  component: History,
} as Meta;

const Template: Story = (args) => <History {...args} />;

export const Basic = Template.bind({});
