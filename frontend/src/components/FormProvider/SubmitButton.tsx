import Button, { Props as ButtonProps } from "components/shared/Button/Button";
import useFormContext from "hooks/useFormContext";

const SubmitButton = (props: ButtonProps) => {
  const { isValid, isEmpty } = useFormContext();

  const disabled = !isValid || isEmpty;

  return <Button {...props} disabled={disabled} />;
};

export default SubmitButton;
