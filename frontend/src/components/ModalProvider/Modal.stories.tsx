import { Story, Meta } from "@storybook/react";

import useModalContext from "../../hooks/useModalContext";
import Button from "../shared/Button/Button";

import ModalProvider, { Props } from "./ModalProvider";

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
        open(<div css={{ width: "150px", height: "150px", backgroundColor: "#fff" }}>모달 컨텐츠</div>);
      }}
    >
      모달 열기
    </Button>
  );
};

Basic.args = {
  children: <TestButton />,
};
