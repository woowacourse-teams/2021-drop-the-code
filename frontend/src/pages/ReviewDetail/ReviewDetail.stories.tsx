import { MemoryRouter, Route } from "react-router-dom";

import { Story, Meta } from "@storybook/react";

import ReviewDetail from "pages/ReviewDetail/ReviewDetail";

export default {
  title: "pages/ReviewDetail",
  component: ReviewDetail,
  decorators: [
    (Story) => (
      <MemoryRouter initialEntries={["/review/1"]}>
        <Route path="/review/:reviewId">
          <Story />
        </Route>
      </MemoryRouter>
    ),
  ],
} as Meta;

const Template: Story = (args) => <ReviewDetail {...args} />;

export const Basic = Template.bind({});

Basic.args = {
  children: <div>로고</div>,
  rightChildren: "내용물",
};
