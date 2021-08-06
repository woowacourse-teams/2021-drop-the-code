import { Story, Meta } from "@storybook/react";

import Carousel, { Props } from "components/Carousel/Carousel";

export default {
  title: "components/Carousel",
  component: Carousel,
} as Meta;

const Template: Story<Props> = (args) => <Carousel {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: [
    <img
      key={0}
      src="https://images.unsplash.com/photo-1627840935425-3d333bb627f8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
    />,
    <img
      key={1}
      src="https://images.unsplash.com/photo-1621184455846-a84bfd77fcbe?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
    />,
    <img
      key={3}
      src="https://images.unsplash.com/photo-1627840935425-3d333bb627f8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
    />,
    <img
      key={4}
      src="https://images.unsplash.com/photo-1621184455846-a84bfd77fcbe?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
    />,
    <img
      key={5}
      src="https://images.unsplash.com/photo-1627840935425-3d333bb627f8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
    />,
    <img
      key={6}
      src="https://images.unsplash.com/photo-1621184455846-a84bfd77fcbe?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
    />,
  ],
};
