import { Suspense, useEffect, useState } from "react";
import { useMutation } from "react-query";
import { useHistory } from "react-router-dom";

import styled, { css } from "styled-components";
import { Role } from "types/review";

import { deleteAuth } from "apis/auth";
import smBlackLogo from "assets/sm-white-github-logo.png";
import Confirm from "components/Confirm/Confirm";
import Loading from "components/Loading/Loading";
import ReviewList from "components/Review/ReviewList/ReviewList";
import MyReviewerInfo from "components/Reviewer/MyReviewerInfo/MyReviewerInfo";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import { Flex, FlexCenter, FlexSpaceBetween } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useModalContext from "hooks/useModalContext";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { COLOR } from "utils/constants/color";
import { ALT, CONFIRM, SUCCESS_MESSAGE } from "utils/constants/message";
import { PATH } from "utils/constants/path";

const Profile = styled(FlexCenter)`
  flex: 1;
  width: 55%;
  padding: 20px;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
  line-height: 1.5625rem;
  max-height: 12.5rem;
`;

const LoginButtonImage = styled.img`
  width: 1.25rem;
  height: 1.25rem;
  margin-right: 0.3125rem;
`;

const Item = styled.li<{ active: boolean }>`
  position: relative;
  ${({ active }) =>
    active &&
    css`
      border-bottom: 1px solid ${COLOR.BLACK};
    `}

  ${({ active }) =>
    active &&
    css`
      :after {
        position: absolute;
        content: "";
        left: 0;
        bottom: -0.125rem;
        width: 100%;
        height: 0.125rem;
        background-color: ${COLOR.BLACK};
      }
    `}
`;

const MyPage = () => {
  const { user, logout } = useAuthContext();
  const { open } = useModalContext();
  const toast = useToastContext();
  const history = useHistory();
  const { revalidate } = useRevalidate();

  const [activeTab, setActiveTab] = useState<Role | null>(null);

  const isReviewer = user?.role === "TEACHER";

  const myTabs: { name: string; mode: Role }[] = isReviewer
    ? [
        { name: "맡은 리뷰", mode: "TEACHER" },
        { name: "요청한 리뷰", mode: "STUDENT" },
      ]
    : [{ name: "요청한 리뷰", mode: "STUDENT" }];

  const deleteAuthMutation = useMutation(() => {
    return revalidate(async () => {
      const response = await deleteAuth();

      if (!response.isSuccess) {
        toast(response.error.errorMessage, { type: "error" });
      } else {
        toast(SUCCESS_MESSAGE.API.AUTH.DELETE);

        logout();
        history.push(PATH.MAIN);
      }

      return response;
    });
  });

  useEffect(() => {
    setActiveTab(myTabs[0].mode);
  }, []);

  if (!user) return <Loading />;

  if (deleteAuthMutation.isLoading) return <Loading />;

  return (
    <>
      <Flex>
        <h2 css={{ fontWeight: 900 }}>{user.name}님 안녕하세요!</h2>
      </Flex>
      <FlexSpaceBetween>
        <Flex css={{ marginBottom: "2.5rem" }}>
          <Profile>
            <Avatar
              imageUrl={user.imageUrl}
              width="6.25rem"
              height="6.25rem"
              css={{ marginRight: "1.25rem" }}
              alt={`${user.name}${ALT.PROFILE_AVATAR}`}
            />
            <Flex css={{ flexDirection: "column" }}>
              <p>{user.name}</p>
              <p>{user.email}</p>
              <a
                href={user.githubUrl}
                target="_blank"
                rel="noopener noreferrer"
                css={{ display: "flex", justifyContent: "center", alignItems: "center" }}
              >
                <LoginButtonImage src={smBlackLogo} alt={ALT.GITHUB_LOGIN_BUTTON} />
                <p>{user.githubUrl}</p>
              </a>
            </Flex>
          </Profile>
        </Flex>
        <Suspense fallback={<Loading />}>{isReviewer && <MyReviewerInfo reviewerId={user.id} />}</Suspense>
      </FlexSpaceBetween>
      <ul
        css={{
          display: "flex",
          height: "1.875rem",
          borderBottom: `1px solid ${COLOR.GRAY_400}`,
          marginBottom: "1.25rem",
        }}
      >
        {myTabs.map(({ name, mode }) => (
          <Item key={name} active={mode === activeTab}>
            <button
              onClick={() => {
                setActiveTab(mode);
              }}
            >
              {name}
            </button>
          </Item>
        ))}
      </ul>
      <Suspense fallback={<Loading />}>{activeTab && <ReviewList id={user.id} mode={activeTab} />}</Suspense>
      <Button
        themeColor="secondary"
        css={{ margin: "1.875rem 0", fontSize: "0.875rem", color: COLOR.GRAY_500 }}
        onClick={() => {
          open(
            <Confirm
              title={CONFIRM.AUTH.DELETE}
              onConfirm={() => {
                deleteAuthMutation.mutate();
              }}
            />
          );
        }}
      >
        회원 탈퇴하기
      </Button>
    </>
  );
};

export default MyPage;
