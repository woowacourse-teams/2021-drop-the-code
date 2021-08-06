import { ERROR_MESSAGE } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";

const reviewFeedBackValidators = {
  content: (content: string) => {
    if (content.length > STANDARD.REVIEW_FEEDBACK.CONTENT.MAX_LENGTH) {
      throw Error(ERROR_MESSAGE.VALIDATION.REVIEW_FEEDBACK.CONTENT);
    }
  },
};

export default reviewFeedBackValidators;
