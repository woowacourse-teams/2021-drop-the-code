import Logout from "../../pages/Logout/Logout";
import Main from "../../pages/Main/Main";
import ReviwerDetail from "../../pages/ReviewerDetail/ReviewerDetail";
import ReviewerRegister from "../../pages/ReviewerRegister/ReviewerRegister";
import ReviewHistory from "../../pages/ReviewHistory/ReviewHistory";
import ReviewRequest from "../../pages/ReviewRequest/ReviewRequest";
import { RouteShape } from "../../types/route";

export const PATH = {
  MAIN: "/",
  REVIWER_DETAIL: "/reviewer/:reviewerId",
  REVIEWER_REGISTER: "/reviewer/register",
  REVIEW_HISTORY: "/review/history",
  REVIEW_REQUEST: "/review/request/:reviewerId",
  LOGOUT: "/logout",
};

export const ROUTE: RouteShape[] = [
  { path: PATH.MAIN, Component: Main, isPrivate: false },
  { path: PATH.REVIWER_DETAIL, Component: ReviwerDetail, isPrivate: false },
  { path: PATH.REVIEWER_REGISTER, Component: ReviewerRegister, isPrivate: true },
  { path: PATH.REVIEW_HISTORY, Component: ReviewHistory, isPrivate: true },
  { path: PATH.REVIEW_REQUEST, Component: ReviewRequest, isPrivate: true },
  { path: PATH.LOGOUT, Component: Logout, isPrivate: true },
  { path: PATH.LOGOUT, Component: Logout, isPrivate: true },
];
