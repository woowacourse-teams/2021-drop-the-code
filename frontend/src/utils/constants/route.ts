import { RouteShape, NavMenuShape, RoleNavMenuShape } from "types/route";

import History from "pages/History/History";
import Main from "pages/Main/Main";
import RedirectOAuth from "pages/RedirectOAuth/RedirectOAuth";
import ReviewDetail from "pages/ReviewDetail/ReviewDetail";
import ReviewerDetail from "pages/ReviewerDetail/ReviewerDetail";
import ReviewerRegister from "pages/ReviewerRegister/ReviewerRegister";
import { PATH } from "utils/constants/path";

export const ROUTE: RouteShape[] = [
  { path: PATH.MAIN, Component: Main, isPrivate: false, exact: true },
  { path: PATH.REVIEWER_REGISTER, Component: ReviewerRegister, isPrivate: true, exact: true },
  { path: PATH.REVIEWER_DETAIL, Component: ReviewerDetail, isPrivate: false, exact: true },
  { path: PATH.HISTORY, Component: History, isPrivate: true, exact: true },
  { path: PATH.REVIEW_DETAIL, Component: ReviewDetail, isPrivate: false, exact: true },
  { path: PATH.REDIRECT_OAUTH, Component: RedirectOAuth, isPrivate: false, exact: true },
];

export const ROLE_MENU: RoleNavMenuShape[] = [
  { to: PATH.REVIEWER_REGISTER, children: "리뷰어 등록하기", isTeacher: false },
];

export const NAV_MENU: NavMenuShape[] = [{ to: PATH.HISTORY, children: "히스토리", isPrivate: true }];
