import { Story, Meta } from "@storybook/react";

import Button from "components/shared/Button/Button";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import useToastContext from "hooks/useToastContext";

import Toast, { Props } from "./Toast";

export default {
  title: "components/ToastButton",
  component: Toast,
} as Meta;

const ToastSuccessButton = () => {
  const toast = useToastContext();

  return (
    <Button
      onClick={() => {
        toast("성공성공성공공성공성공성공성공성공성공성공");
      }}
    >
      success toast
    </Button>
  );
};

const ToastErrorButton = () => {
  const toast = useToastContext();

  return (
    <Button
      onClick={() => {
        toast("실패실패실패실패실패실패실패실패실패실패", { type: "error" });
      }}
    >
      error toast
    </Button>
  );
};

const Template: Story<Props> = () => (
  <FlexCenter css={{ width: "100%" }}>
    <ToastSuccessButton />
    <ToastErrorButton />
  </FlexCenter>
);

export const ToastButton = Template.bind({});
