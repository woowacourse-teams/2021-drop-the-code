import { Story, Meta } from "@storybook/react";

import { reviewers } from "../../../__mock__/reviewers";

import ContentBox, { Props } from "./ContentBox";

export default {
  title: "components/ContentBox",
  component: ContentBox,
} as Meta;

const Template: Story<Props> = (args) => <ContentBox {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  title: reviewers[0].title,
  children: reviewers[0].content,
};
