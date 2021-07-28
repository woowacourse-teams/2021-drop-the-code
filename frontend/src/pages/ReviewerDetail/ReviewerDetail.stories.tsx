import { MemoryRouter, Route } from "react-router-dom";

import { Story, Meta } from "@storybook/react";

import ReviewerDetail from "pages/ReviewerDetail/ReviewerDetail";

export default {
  title: "pages/ReviewerDetail",
  component: ReviewerDetail,
  decorators: [
    (Story) => (
      <MemoryRouter initialEntries={["/reviewer/1"]}>
        <Route path="/reviewer/:reviewerId">
          <Story />
        </Route>
      </MemoryRouter>
    ),
  ],
} as Meta;

const Template: Story = (args) => <ReviewerDetail {...args} />;

export const Basic = Template.bind({});
