import { STANDARD } from "./standard";

export const ERROR_MESSAGE = {
  REVIEWER_REGISTER: {
    TITLE: `${STANDARD.REVIEWER_REGISTER.TITLE.MAX_LENGTH}자 이내로 작성해주세요.`,
    CAREER: `${STANDARD.REVIEWER_REGISTER.CAREER.MIN}에서 ${STANDARD.REVIEWER_REGISTER.CAREER.MAX}까지만 입력 가능합니다.`,
    CONTENT: `${STANDARD.REVIEWER_REGISTER.CONTENT.MAX_LENGTH}자 이내로 작성해주세요.`,
  },
};

export const PLACE_HOLDER = {
  REVIEWER_REGISTER: {
    TITLE: "리뷰어 목록에 나타날 타이틀입니다. 본인을 나타낼 수 있는 한 줄 소개를 작성해주세요.",
    CAREER: "3년차",
    CONTENT: `안녕하세요. ㅇㅇㅇ입니다.\nㅇㅇ 회사에서 프론트엔드로 3년간 근무했습니다.\n리뷰 요청을 주실 때, 중점적으로 코드리뷰 받고 싶은 부분을 기재해주시면 좋습니다.\n\n· 경력: ~~\n· 이력: ~~\n· 블로그: ~~\n· 링크: ~~`,
  },
};
