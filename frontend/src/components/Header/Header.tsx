import { Link, NavLink } from "react-router-dom";

import styled from "styled-components";

import Logo from "assets/logo.svg";
import GithubOAuth from "components/Auth/OAuth/GithubOAuth";
import Button from "components/shared/Button/Button";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import Navigation from "components/shared/Navigation/Navigation";
import useAuthContext from "hooks/useAuthContext";
import useModalContext from "hooks/useModalContext";
import { PATH } from "utils/constants/path";
import { NAV_MENU, ROLE_MENU } from "utils/constants/route";

const NavigationButton = styled(Button)`
  :hover {
    color: ${({ theme }) => theme.common.color.primary};
  }
`;

const NavigationLink = styled(NavLink)`
  :hover {
    color: ${({ theme }) => theme.common.color.primary};
  }

  &.active {
    color: ${({ theme }) => theme.common.color.primary};
    font-weight: 900;
  }
`;

const Header = () => {
  const { user, logout } = useAuthContext();
  const { open } = useModalContext();

  return (
    <Navigation
      leftChildren={
        <h1>
          <Link to={PATH.MAIN}>
            <FlexCenter>
              <Logo width={200} height={50} />
            </FlexCenter>
          </Link>
        </h1>
      }
    >
      {!!user &&
        ROLE_MENU.filter(({ isTeacher }) => isTeacher === (user.role === "TEACHER")).map(({ to, children }) => (
          <NavigationLink key={to} to={to} activeClassName="active">
            {children}
          </NavigationLink>
        ))}
      {NAV_MENU.filter(({ isPrivate }) => !isPrivate || isPrivate === !!user).map(({ to, children }) => (
        <NavigationLink key={to} to={to} activeClassName="active">
          {children}
        </NavigationLink>
      ))}
      {!user && (
        <NavigationButton
          themeColor="primary"
          hover={false}
          css={{ fontWeight: 900 }}
          onClick={() => open(<GithubOAuth />)}
        >
          로그인
        </NavigationButton>
      )}
      {!!user && (
        <NavigationButton themeColor="primary" hover={false} css={{ fontWeight: 900 }} onClick={() => logout()}>
          로그아웃
        </NavigationButton>
      )}
    </Navigation>
  );
};

export default Header;
