import { Story, Meta } from "@storybook/react";

import NotFound from "pages/NotFound/NotFound";

export default {
  title: "pages/NotFound",
  component: NotFound,
} as Meta;

const Template: Story = (args) => <NotFound {...args} />;

export const Basic = Template.bind({});
