import { ERROR_MESSAGE } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";

const reviewRequestValidators = {
  title: (title: string) => {
    if (title.length >= STANDARD.REVIEW_REQUEST.TITLE.MAX_LENGTH) {
      throw Error(ERROR_MESSAGE.VALIDATION.REVIEW_REQUEST.TITLE);
    }
  },
  prUrl: (prUrl: string) => {
    if (prUrl.length > STANDARD.REVIEW_REQUEST.PR_URL.MAX_LENGTH) {
      throw Error(ERROR_MESSAGE.VALIDATION.REVIEW_REQUEST.PR_URL.LENGTH);
    }

    if (prUrl.startsWith("https://github.com/") && prUrl.includes("/pull/")) return;

    throw Error(ERROR_MESSAGE.VALIDATION.REVIEW_REQUEST.PR_URL.FORMAT);
  },
  content: (content: string) => {
    if (content.length > STANDARD.REVIEW_REQUEST.CONTENT.MAX_LENGTH) {
      throw Error(ERROR_MESSAGE.VALIDATION.REVIEW_REQUEST.CONTENT);
    }
  },
};

export default reviewRequestValidators;
