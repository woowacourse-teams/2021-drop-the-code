import { Link } from "react-router-dom";

import styled from "styled-components";

import useAuthContext from "hooks/useAuthContext";
import { COLOR } from "utils/constants/color";
import { NAV_MENU, ROLE_MENU } from "utils/constants/route";

const Inner = styled.div`
  padding: 0.625rem 0;
  width: 9.375rem;
`;

const Welcome = styled.div`
  padding: 0.625rem 0.625rem;
  line-height: 1.25rem;
  color: ${COLOR.GRAY_500};
`;

const MenuLink = styled(Link)`
  width: 100%;

  :hover {
    background-color: ${COLOR.INDIGO_500};
    color: white;
  }
`;

const LineDivider = styled.div`
  display: block;
  height: 0;
  border-top: 1px solid lightGray;
  margin: 0.3125rem 0;
`;

const Logout = styled.div`
  padding: 0.625rem;
  color: ${COLOR.GRAY_700};

  :hover {
    background-color: ${COLOR.INDIGO_500};
    color: white;
    border-radius: 0.25rem;
  }
`;

const DropDownMenu = () => {
  const { user, logout } = useAuthContext();

  return (
    <Inner>
      {!!user && (
        <>
          <Welcome>
            <div>환영합니다!</div>
            <div>{user.name}님</div>
          </Welcome>
          <LineDivider />
        </>
      )}
      {!!user &&
        ROLE_MENU.filter(({ isTeacher }) => isTeacher === (user.role === "TEACHER")).map(({ to, children }) => (
          <MenuLink key={to} to={to}>
            {children}
          </MenuLink>
        ))}
      {!!user &&
        NAV_MENU.filter(({ isPrivate }) => isPrivate).map(({ to, children }) => (
          <MenuLink key={to} to={to}>
            {children}
          </MenuLink>
        ))}
      <LineDivider></LineDivider>
      <Logout onClick={() => logout()}>로그아웃</Logout>
    </Inner>
  );
};

export default DropDownMenu;
