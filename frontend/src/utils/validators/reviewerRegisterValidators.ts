import { ERROR_MESSAGE } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";

const reviewerRegisterValidators = {
  title: (title: string) => {
    if (title.length >= STANDARD.REVIEWER_REGISTER.TITLE.MAX_LENGTH) {
      throw Error(ERROR_MESSAGE.VALIDATION.REVIEWER_REGISTER.TITLE);
    }
  },
  career: (career: string) => {
    if (
      Number(career) < STANDARD.REVIEWER_REGISTER.CAREER.MIN ||
      Number(career) > STANDARD.REVIEWER_REGISTER.CAREER.MAX
    ) {
      throw Error(ERROR_MESSAGE.VALIDATION.REVIEWER_REGISTER.CAREER);
    }
  },
  content: (content: string) => {
    if (content.length > STANDARD.REVIEWER_REGISTER.CONTENT.MAX_LENGTH) {
      throw Error(ERROR_MESSAGE.VALIDATION.REVIEWER_REGISTER.CONTENT);
    }
  },
};

export default reviewerRegisterValidators;
