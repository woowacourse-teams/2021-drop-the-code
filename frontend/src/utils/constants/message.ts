import { STANDARD } from "utils/constants/standard";

export const SUCCESS_MESSAGE = {
  API: {
    AUTH: {
      DELETE: "회원을 탈퇴했습니다.",
    },
    REVIEW: {
      REQUEST: "리뷰를 요청했습니다.",
      DELETE: "리뷰를 삭제했습니다.",
      DENY: "리뷰를 거절했습니다.",
      ACCEPT: "리뷰를 승인했습니다.",
      EDIT: "리뷰를 수정했습니다.",
      COMPLETE: "리뷰를 완료했습니다.",
      FINISH: "리뷰를 완료했습니다.",
    },
    REVIEWER: {
      REGISTER: "리뷰어로 등록했습니다.",
      DELETE: "리뷰어 정보를 삭제했습니다.",
      EDIT: "리뷰어 정보를 수정했습니다.",
    },
  },
};

export const ERROR_MESSAGE = {
  VALIDATION: {
    REVIEWER_REGISTER: {
      TITLE: `${STANDARD.REVIEWER_REGISTER.TITLE.MAX_LENGTH}자 이내로 작성해주세요.`,
      CAREER: `${STANDARD.REVIEWER_REGISTER.CAREER.MIN}에서 ${STANDARD.REVIEWER_REGISTER.CAREER.MAX}까지만 입력 가능합니다.`,
      CONTENT: `${STANDARD.REVIEWER_REGISTER.CONTENT.MAX_LENGTH}자 이내로 작성해주세요.`,
      TECH_SPEC: "기술스택을 선택해주세요.",
    },
    REVIEW_REQUEST: {
      TITLE: `${STANDARD.REVIEW_REQUEST.TITLE.MAX_LENGTH}자 이내로 작성해주세요.`,
      PR_URL: {
        FORMAT: `올바른 Pull Request URL 형식이 아닙니다.`,
        LENGTH: `${STANDARD.REVIEW_REQUEST.PR_URL.MAX_LENGTH}자 이내로 작성해주세요.`,
      },
      CONTENT: `${STANDARD.REVIEW_REQUEST.CONTENT.MAX_LENGTH}자 이내로 작성해주세요.`,
    },
    REVIEW_FEEDBACK: {
      CONTENT: `${STANDARD.REVIEW_FEEDBACK.CONTENT.MAX_LENGTH}자 이내로 작성해주세요.`,
    },
  },
  API: {
    AUTH: {},
    REVIEW: {
      GET_LIST: "리뷰 목록을 조회하는데 실패했습니다.",
    },
    REVIEWER: {
      REGISTER: "리뷰어 등록에 실패했습니다.",
    },
  },
  AUTH: {
    REQUIRED: "로그인이 필요합니다.",
    ALEADY_REGISTERED_REVIEWER: "이미 리뷰어로 등록되어 있습니다.",
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
  REVIEW_FEEDBACK: {
    CONTENT: "리뷰에 대한 솔직한 피드백을 남겨주세요.",
  },
};

export const ALT = {
  REVIEW_DETAIL_NOTIFICATION:
    "리뷰어는 리뷰를 남긴 후, 하단의 리뷰완료 버튼을 눌러주세요. 리뷰 요청인은 리뷰를 받은 후 리뷰 종료 버튼을 눌러주세요.",
  GITHUB_LOGIN_BUTTON: "깃허브 로그인 버튼 이미지",
  REVIEWER_PROFILE_AVATAR: "리뷰어 프로필 이미지",
  GITHUB_LOGO: "깃허브 로고 이미지",
  NO_REVIEW: "리뷰 없음 이미지",
};

export const CONFIRM = {
  AUTH: {
    DELETE: "정말 탈퇴하시겠습니까?",
  },
  REVIEWER: {
    DELETE: "리뷰어 정보를 삭제하시겠습니까?",
  },
};
