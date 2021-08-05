import { Suspense, useEffect, useState } from "react";
import { useMutation } from "react-query";
import { Redirect, useHistory } from "react-router-dom";

import axios from "axios";
import styled, { css } from "styled-components";
import { Role } from "types/review";

import { deleteAuth } from "apis/auth";
import SmBlackLogo from "assets/sm-white-github-logo.png";
import Confirm from "components/Confirm/Confirm";
import Loading from "components/Loading/Loading";
import ReviewList from "components/Review/ReviewList/ReviewList";
import MyReviewerInfo from "components/Reviewer/MyReviewerInfo/MyReviewerInfo";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import { Flex, FlexAlignCenter, FlexSpaceBetween } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useLocalStorage from "hooks/useLocalStorage";
import useModalContext from "hooks/useModalContext";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { COLOR } from "utils/constants/color";
import { LOCAL_STORAGE_KEY } from "utils/constants/key";
import { ALT, CONFIRM, SUCCESS_MESSAGE } from "utils/constants/message";
import { PATH } from "utils/constants/path";

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
  const [accessToken] = useLocalStorage<string | null>(LOCAL_STORAGE_KEY.ACCESS_TOKEN, null);

  const [activeTab, setActiveTab] = useState<Role | null>(null);

  if (!user) return <Redirect to={PATH.MAIN} />;

  const isReviewer = user.role === "TEACHER";

  const myTabs: { name: string; mode: Role }[] = isReviewer
    ? [
        { name: "맡은 리뷰", mode: "TEACHER" },
        { name: "요청한 리뷰", mode: "STUDENT" },
      ]
    : [{ name: "요청한 리뷰", mode: "STUDENT" }];

  useEffect(() => {
    setActiveTab(myTabs[0].mode);
  }, []);

  const { revalidate } = useRevalidate();

  axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

  const deleteAuthMutation = useMutation(() => {
    return revalidate(async () => {
      const response = await deleteAuth();

      if (response.isSuccess) {
        toast(SUCCESS_MESSAGE.API.AUTH.DELETE);
      }

      return response;
    });
  });

  if (deleteAuthMutation.isLoading) return <Loading />;

  const deleteAuthInfo = () => {
    deleteAuthMutation.mutate();
    logout();
    history.push(PATH.MAIN);
  };

  return (
    <>
      <FlexAlignCenter>
        <h2 css={{ fontWeight: 900 }}>{user.name}님 안녕하세요!</h2>
      </FlexAlignCenter>
      <FlexSpaceBetween css={{ alignItems: "center" }}>
        <Flex css={{ marginBottom: "2.5rem" }}>
          <Avatar imageUrl={user.imageUrl} width="6.25rem" height="6.25rem" css={{ marginRight: "1.25rem" }} />
          <Flex css={{ flexDirection: "column", justifyContent: "center" }}>
            <p css={{ marginBottom: "0.625rem" }}>{user.name}</p>
            <p css={{ marginBottom: "0.625rem" }}>{user.email}</p>
            <a
              href={user.githubUrl}
              target="_blank"
              rel="noopener noreferrer"
              css={{ display: "flex", justifyContent: "center", alignItems: "center" }}
            >
              <LoginButtonImage src={SmBlackLogo} alt={ALT.GITHUB_LOGIN_BUTTON} />
              <p>{user.githubUrl}</p>
            </a>
          </Flex>
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
                deleteAuthInfo();
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
