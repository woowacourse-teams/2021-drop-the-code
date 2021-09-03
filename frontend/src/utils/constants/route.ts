import { lazy } from "react";

import { RouteShape, NavMenuShape, RoleNavMenuShape } from "types/route";

import { PATH } from "utils/constants/path";

const Guide = lazy(() => import("pages/Guide/Guide"));
const Main = lazy(() => import("pages/Main/Main"));
const MyPage = lazy(() => import("pages/MyPage/MyPage"));
const RedirectOAuth = lazy(() => import("pages/RedirectOAuth/RedirectOAuth"));
const ReviewDetail = lazy(() => import("pages/ReviewDetail/ReviewDetail"));
const ReviewerDetail = lazy(() => import("pages/ReviewerDetail/ReviewerDetail"));
const ReviewerRegister = lazy(() => import("pages/ReviewerRegister/ReviewerRegister"));
const ReviewerSearch = lazy(() => import("pages/ReviewerSearch/ReviewerSearch"));
const Chatting = lazy(() => import("pages/Chatting/Chatting"));

export const ROUTE: RouteShape[] = [
  { path: PATH.MAIN, Component: Main, isPrivate: false, exact: true },
  { path: PATH.REVIEWER_SEARCH, Component: ReviewerSearch, isPrivate: false, exact: true },
  { path: PATH.REVIEWER_REGISTER, Component: ReviewerRegister, isPrivate: true, exact: true },
  { path: PATH.REVIEWER_DETAIL, Component: ReviewerDetail, isPrivate: false, exact: true },
  { path: PATH.MY_PAGE, Component: MyPage, isPrivate: true, exact: true },
  { path: PATH.REVIEW_DETAIL, Component: ReviewDetail, isPrivate: false, exact: true },
  { path: PATH.REDIRECT_OAUTH, Component: RedirectOAuth, isPrivate: false, exact: true },
  { path: PATH.GUIDE, Component: Guide, isPrivate: false, exact: true },
  { path: PATH.CHATTING, Component: Chatting, isPrivate: true, exact: true },
];

export const ROLE_MENU: RoleNavMenuShape[] = [
  { to: PATH.REVIEWER_REGISTER, children: "리뷰어 등록하기", isTeacher: false },
];

export const NAV_MENU: NavMenuShape[] = [
  { to: PATH.GUIDE, children: "가이드", isPrivate: false },
  { to: PATH.MY_PAGE, children: "마이페이지", isPrivate: true },
  { to: PATH.CHATTING, children: "메신저", isPrivate: true },
];
