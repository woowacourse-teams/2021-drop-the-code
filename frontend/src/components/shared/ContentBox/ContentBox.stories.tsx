import { Story, Meta } from "@storybook/react";

import { reviewers } from "__mock__/data/reviewers";
import ContentBox, { Props } from "components/shared/ContentBox/ContentBox";

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
