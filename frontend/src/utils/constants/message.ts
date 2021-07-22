import { STANDARD } from "./standard";

export const ERROR_MESSAGE = {
  REVIEWER_REGISTER: {
    TITLE: `${STANDARD.REVIEWER_REGISTER.TITLE.MAX_LENGTH}자 이내로 작성해주세요.`,
    CAREER: `${STANDARD.REVIEWER_REGISTER.CAREER.MIN}에서 ${STANDARD.REVIEWER_REGISTER.CAREER.MAX}까지만 입력 가능합니다.`,
    CONTENT: `${STANDARD.REVIEWER_REGISTER.CONTENT.MAX_LENGTH}자 이내로 작성해주세요.`,
  },
  REVIEW_REQUEST: {
    TITLE: `${STANDARD.REVIEW_REQUEST.TITLE.MAX_LENGTH}자 이내로 작성해주세요.`,
    PR_URL: {
      FORMAT: `올바른 Pull Request URL 형식이 아닙니다.`,
      LENGTH: `${STANDARD.REVIEW_REQUEST.PR_URL.MAX_LENGTH}자 이내로 작성해주세요.`,
    },
    CONTENT: `${STANDARD.REVIEW_REQUEST.CONTENT.MAX_LENGTH}자 이내로 작성해주세요.`,
  },
};

export const PLACE_HOLDER = {
  REVIEWER_REGISTER: {
    TITLE: "리뷰어 목록에 나타날 타이틀입니다. 본인을 나타낼 수 있는 한 줄 소개를 작성해주세요.",
    CAREER: "3년차",
    CONTENT: `안녕하세요. ㅇㅇㅇ입니다.\nㅇㅇ 회사에서 프론트엔드로 3년간 근무했습니다.\n리뷰 요청을 주실 때, 중점적으로 코드리뷰 받고 싶은 부분을 기재해주시면 좋습니다.\n\n· 경력: ~~\n· 이력: ~~\n· 블로그: ~~\n· 링크: ~~`,
  },
  REVIEW_REQUEST: {
    TITLE: "리뷰 요청 제목입니다.",
    PR_URL: "예시: https://github.com/woowacourse-teams/2021-drop-the-code/pull/113",
    CONTENT: `안녕하세요. 개발 공부 3개월차 코린이입니다.\n리액트를 처음 사용해보았는데, 잘 사용하고 있는 것인지 궁금해서 리뷰요청을 드립니다.\n리액트 훅을 사용한 부분을 중점적으로 봐주시면 좋겠습니다. 감사합니다.\n`,
  },
};
