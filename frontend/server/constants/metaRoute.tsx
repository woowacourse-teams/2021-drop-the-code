import { PATH } from "utils/constants/path";

export const META_ROUTE = [
  { path: PATH.MAIN, meta: <meta name="description" content="내 코드를 한 단계 성장시켜줄 리뷰어를 만나보세요." /> },
  {
    path: PATH.REVIEWER_SEARCH,
    meta: <meta name="description" content="원하는 리뷰어를 선택하고 정보를 확인하세요." />,
  },
  {
    path: PATH.REVIEWER_DETAIL,
    meta: <meta name="description" content="리뷰어의 정보를 확인하고 코드리뷰를 요청하세요." />,
  },
  { path: PATH.REVIEW_DETAIL, meta: <meta name="description" content="이전에 진행되었던 리뷰 기록을 확인하세요." /> },
  { path: PATH.GUIDE, meta: <meta name="description" content="코드봐줘 이용방법을 확인하세요." /> },
];

export default META_ROUTE;
