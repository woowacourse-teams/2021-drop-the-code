import Main from "../../pages/Main/Main";
import ReviwerDetail from "../../pages/ReviewerDetail/ReviewerDetail";
import ReviewerRegister from "../../pages/ReviewerRegister/ReviewerRegister";
import ReviewHistory from "../../pages/ReviewHistory/ReviewHistory";
import ReviewRequest from "../../pages/ReviewRequest/ReviewRequest";
import { RouteShape, NavMenuShape } from "../../types/route";

export const PATH = {
  MAIN: "/",
  REVIWER_DETAIL: "/reviewer/:reviewerId",
  REVIEWER_REGISTER: "/reviewer/register",
  REVIEW_HISTORY: "/review/history",
  REVIEW_REQUEST: "/review/request/:reviewerId",
};

export const ROUTE: RouteShape[] = [
  { path: PATH.MAIN, Component: Main, isPrivate: false },
  { path: PATH.REVIWER_DETAIL, Component: ReviwerDetail, isPrivate: false },
  { path: PATH.REVIEWER_REGISTER, Component: ReviewerRegister, isPrivate: true },
  { path: PATH.REVIEW_HISTORY, Component: ReviewHistory, isPrivate: true },
  { path: PATH.REVIEW_REQUEST, Component: ReviewRequest, isPrivate: true },
];

export const NAV_MENU: NavMenuShape[] = [
  { to: PATH.REVIEWER_REGISTER, children: "리뷰어 등록하기", isPrivate: true },
  { to: PATH.REVIEW_HISTORY, children: "히스토리", isPrivate: true },
];
