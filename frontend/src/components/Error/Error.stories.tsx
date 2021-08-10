import { Story, Meta } from "@storybook/react";

import Error, { Props } from "components/Error/Error";

export default {
  title: "components/Error",
  component: Error,
} as Meta;

const Template: Story<Props> = (args) => <Error {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  resetError: () => {},
};
