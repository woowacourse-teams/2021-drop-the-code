import { Story, Meta } from "@storybook/react";

import GithubOAuth from "components/Auth/OAuth/GithubOAuth";
import ModalProvider, { Props } from "components/ModalProvider/ModalProvider";
import Button from "components/shared/Button/Button";
import useModalContext from "hooks/useModalContext";

export default {
  title: "components/ModalProvider",
  component: ModalProvider,
} as Meta;

const Template: Story<Props> = (args) => <ModalProvider {...args} />;

export const Basic = Template.bind({});

const TestButton = () => {
  const { open } = useModalContext();

  return (
    <Button
      onClick={() => {
        open(<GithubOAuth />);
      }}
    >
      모달 열기
    </Button>
  );
};

Basic.args = {
  children: <TestButton />,
};
