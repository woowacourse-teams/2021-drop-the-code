import { NavLink } from "react-router-dom";

import styled, { css } from "styled-components";

import notFoundGif from "assets/not-found.gif";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import { PATH } from "utils/constants/path";

const HomeLink = styled(NavLink)`
  background-color: ${({ theme }) => theme.common.color.primary};
  color: ${({ theme }) => theme.common.color.light};
  border-radius: ${({ theme }) => theme.common.shape.pill};
  padding: 0.9375rem 1.5625rem;

  :hover {
    box-shadow: ${css`
      0.125rem 0.125rem 0.25rem rgb(0 0 0 / 30%);
    `};
  }

  :active {
    background-color: ${({ theme }) => css`
      ${theme.components.button.primary.activeBg};
    `};
  }

  font-size: 24px;
`;

const Image = styled.img`
  width: 31.25rem;
  padding-bottom: 6.25rem;
`;

const NotFound = () => (
  <FlexCenter css={{ position: "absolute", flexDirection: "column", width: "100vw", height: "100vh" }}>
    <Image src={notFoundGif} alt="404 not found 이미지" />
    <HomeLink to={PATH.MAIN}>GO BACK HOME</HomeLink>
  </FlexCenter>
);

export default NotFound;
