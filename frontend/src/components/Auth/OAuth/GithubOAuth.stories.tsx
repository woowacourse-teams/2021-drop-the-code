import { Story, Meta } from "@storybook/react";

import GithubOAuth from "components/Auth/OAuth/GithubOAuth";

export default {
  title: "components/GithubOAuth",
  component: GithubOAuth,
} as Meta;

const Template: Story = () => <GithubOAuth />;

export const Basic = Template.bind({});

Basic.args = {};
