import styled from "styled-components";

import LgWhiteLogo from "../../../assets/lg-white-github-logo.png";
import SmBlackLogo from "../../../assets/sm-black-github-logo.png";
import { FlexCenter } from "../../shared/Flexbox/Flexbox";

const Inner = styled(FlexCenter)`
  width: 18.75rem;
  height: 18.75rem;
  flex-direction: column;
  justify-content: space-evenly;
`;

const LoginAnchor = styled.a`
  padding: 0.625rem;
  background-color: ${({ theme }) => theme.common.color.dark};
  color: ${({ theme }) => theme.common.color.light};
  border-radius: ${({ theme }) => theme.common.shape.rounded};
`;

const LoginButtonImage = styled.img`
  width: 1.25rem;
  height: 1.25rem;
  margin-right: 0.625rem;
`;

const GithubOAuth = () => {
  return (
    <Inner>
      <img src={LgWhiteLogo} alt="깃허브 로고 이미지" />
      <LoginAnchor
        href={`https://github.com/login/oauth/authorize?client_id=${process.env.GITHUB_OAUTH_CLIENT_ID}&redirect_uri=${process.env.CLIENT_BASE_URL}${process.env.GITHUB_OAUTH_REDIRECT_URL}`}
      >
        <FlexCenter>
          <LoginButtonImage src={SmBlackLogo} alt="깃허브 로그인 버튼 이미지" />
          Github으로 로그인 하기
        </FlexCenter>
      </LoginAnchor>
    </Inner>
  );
};

export default GithubOAuth;
