import { Story, Meta } from "@storybook/react";

import Main from "../Main/Main";

export default {
  title: "pages/Main",
  component: Main,
} as Meta;

const Template: Story = (args) => <Main {...args} />;

export const Basic = Template.bind({});
