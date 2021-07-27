import styled from "styled-components";

import ErrorImage from "assets/error.png";
import Button from "components/shared/Button/Button";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";

const Image = styled.img`
  width: 31.25rem;
  padding-bottom: 6.25rem;
`;

export interface Props {
  resetError: () => void;
}

const Error = ({ resetError }: Props) => {
  return (
    <FlexCenter css={{ position: "absolute", flexDirection: "column", width: "100vw", height: "100vh" }}>
      <Image src={ErrorImage} alt="404 not found 이미지" />
      <Button
        shape="pill"
        onClick={() => {
          resetError();
        }}
        css={{ fontSize: "24px", padding: "0.9375rem 1.5625rem" }}
      >
        이전 페이지로 돌아가기
      </Button>
    </FlexCenter>
  );
};

export default Error;
