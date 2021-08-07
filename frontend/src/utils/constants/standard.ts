export const STANDARD = {
  REVIEWER_REGISTER: {
    TITLE: {
      MAX_LENGTH: 50,
    },
    CAREER: {
      MIN: 0,
      MAX: 50,
    },
    CONTENT: {
      MAX_LENGTH: 5000,
    },
  },
  REVIEW_REQUEST: {
    TITLE: {
      MAX_LENGTH: 50,
    },
    PR_URL: {
      MAX_LENGTH: 200,
    },
    CONTENT: {
      MAX_LENGTH: 1000,
    },
  },
  REVIEW_FEEDBACK: {
    MIN_GRADE: 1,
    MAX_GRADE: 5,
    CONTENT: {
      MAX_LENGTH: 1000,
    },
  },
};
