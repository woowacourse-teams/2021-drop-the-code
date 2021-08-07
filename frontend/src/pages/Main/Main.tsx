import { NavLink } from "react-router-dom";

import styled from "styled-components";

import guideImage from "assets/guide.svg";
import registerImage from "assets/register.svg";
import searchImage from "assets/search.svg";
import { Flex, FlexCenter, FlexSpaceBetween } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";
import { PATH } from "utils/constants/path";

const Inner = styled(Flex)`
  max-width: ${({ theme }) => theme.common.layout.lg};
`;

const EntryBoxWrapper = styled(FlexSpaceBetween)`
  flex-direction: column;
  width: ${({ theme }) => theme.common.layout.lg};
  height: 65vh;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const EntryBox = styled(FlexCenter)`
  padding: 4.375rem 130px;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.bumped};
  transition: box-shadow 0.3s;
  :hover {
    box-shadow: ${({ theme }) => theme.common.boxShadow.pressed};
    background-color: ${COLOR.GRAY_100};
  }
`;

const EntryBoxTitle = styled.div`
  font-size: 1.5rem;
  font-weight: 900;
`;

const SearchImage = styled(searchImage)`
  width: 5rem;
  height: 5rem;
  margin-right: 1.25rem;
  stroke: ${({ theme }) => theme.common.color.primary};
  fill: ${({ theme }) => theme.common.color.primary};
`;

const RegisterImage = styled(registerImage)`
  width: 5rem;
  height: 5rem;
  margin-right: 1.25rem;
  stroke: ${({ theme }) => theme.common.color.primary};
  fill: ${({ theme }) => theme.common.color.primary};
`;

const GuideImage = styled(guideImage)`
  width: 5rem;
  height: 5rem;
  margin-right: 1.25rem;
  stroke: ${({ theme }) => theme.common.color.primary};
  fill: ${({ theme }) => theme.common.color.primary};
`;

const Main = () => {
  return (
    <Inner>
      <EntryBoxWrapper>
        <FlexSpaceBetween>
          <NavLink to={PATH.REVIEWER_SEARCH}>
            <EntryBox>
              <SearchImage />
              <EntryBoxTitle>리뷰어 찾기</EntryBoxTitle>
            </EntryBox>
          </NavLink>
          <NavLink to={PATH.REVIEWER_REGISTER}>
            <EntryBox>
              <RegisterImage />
              <EntryBoxTitle>리뷰어 등록하기</EntryBoxTitle>
            </EntryBox>
          </NavLink>
        </FlexSpaceBetween>
        <NavLink to={PATH.GUIDE}>
          <EntryBox css={{ width: "100%" }}>
            <GuideImage />
            <EntryBoxTitle>코드봐줘 이용 가이드</EntryBoxTitle>
          </EntryBox>
        </NavLink>
      </EntryBoxWrapper>
    </Inner>
  );
};

export default Main;
