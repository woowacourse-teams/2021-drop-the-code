import { useState } from "react";
import { Link, NavLink } from "react-router-dom";

import styled from "styled-components";

import DownArrowSvg from "assets/down-arrow.svg";
import Logo from "assets/logo.svg";
import GithubOAuth from "components/Auth/OAuth/GithubOAuth";
import DropDownMenu from "components/DropDownMenu/DropDownMenu";
import MenuItemButton from "components/MenuItemButton/MenuItemButton";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import Navigation from "components/shared/Navigation/Navigation";
import useAuthContext from "hooks/useAuthContext";
import useModalContext from "hooks/useModalContext";
import { PATH } from "utils/constants/path";
import { NAV_MENU } from "utils/constants/route";

const NavigationButton = styled(Button)`
  font-weight: 900;
`;

const UpArrow = styled(DownArrowSvg)`
  transform: rotate(180deg);
  width: 1rem;
  height: 1rem;
  fill: white;
  position: absolute;
  top: 40px;
  right: 22px;
  z-index: 10;
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
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const { user } = useAuthContext();
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
      {NAV_MENU.filter(({ isPrivate }) => !isPrivate).map(({ to, children }) => (
        <NavigationLink key={to} to={to} activeClassName="active">
          {children}
        </NavigationLink>
      ))}
      {!user && (
        <NavigationButton themeColor="primary" hover={false} onClick={() => open(<GithubOAuth />)}>
          로그인
        </NavigationButton>
      )}
      {!!user && (
        <>
          <MenuItemButton
            themeColor="secondary"
            hover={false}
            contents={() => <DropDownMenu />}
            onClick={() => setDropdownOpen(!dropdownOpen)}
          >
            <Avatar imageUrl={user.imageUrl} width="2.5rem" />
            {dropdownOpen && <UpArrow />}
          </MenuItemButton>
        </>
      )}
    </Navigation>
  );
};

export default Header;
