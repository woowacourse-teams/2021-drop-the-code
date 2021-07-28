import { Link, NavLink } from "react-router-dom";

import Logo from "assets/logo.svg";
import GithubOAuth from "components/Auth/OAuth/GithubOAuth";
import Button from "components/shared/Button/Button";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import Navigation from "components/shared/Navigation/Navigation";
import useAuthContext from "hooks/useAuthContext";
import useModalContext from "hooks/useModalContext";
import { PATH } from "utils/constants/path";
import { NAV_MENU } from "utils/constants/route";

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
      <>
        {NAV_MENU.filter(({ isPrivate }) => isPrivate === !!user).map(({ to, children }) => (
          <NavLink key={to} to={to}>
            {children}
          </NavLink>
        ))}
        {!user && (
          <Button themeColor="secondary" hover={false} css={{ fontWeight: 900 }} onClick={() => open(<GithubOAuth />)}>
            로그인
          </Button>
        )}
        {!!user && (
          <Button themeColor="secondary" hover={false} css={{ fontWeight: 900 }} onClick={() => logout()}>
            로그아웃
          </Button>
        )}
      </>
    </Navigation>
  );
};

export default Header;
