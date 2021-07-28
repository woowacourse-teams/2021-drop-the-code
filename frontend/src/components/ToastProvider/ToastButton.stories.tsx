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
        toast("리뷰요청을 성공했습니다.");
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
        toast("요청에 실패했습니다. 요청에 실패했습니다!", { type: "error" });
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
