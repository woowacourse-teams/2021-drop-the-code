import { useMutation } from "react-query";

import { requestReview } from "../../../apis/review";
import useAuthContext from "../../../hooks/useAuthContext";
import { ReviewRequestFormData } from "../../../types/review";
import { COLOR } from "../../../utils/constants/color";
import { PLACE_HOLDER } from "../../../utils/constants/message";
import { LAYOUT } from "../../../utils/constants/size";
import { STANDARD } from "../../../utils/constants/standard";
import reviewRequestValidators from "../../../utils/validators/reviewRequestValidators";
import FormProvider from "../../FormProvider/FormProvider";
import InputField from "../../FormProvider/InputField";
import TextareaField from "../../FormProvider/TextareaField";
import Loading from "../../Loading/Loading";
import Button from "../../shared/Button/Button";
import { Flex } from "../../shared/Flexbox/Flexbox";

interface Props {
  teacherId: number;
}

const ReviewRequest = ({ teacherId }: Props) => {
  const { user } = useAuthContext();

  const mutation = useMutation(
    (reviewRequestFormData: ReviewRequestFormData) => {
      return requestReview(reviewRequestFormData);
    },
    {
      onSuccess: () => {
        alert("성공");
      },
      onError: () => {
        alert("에러");
      },
    }
  );

  if (mutation.isLoading) return <Loading />;

  return (
    <div css={{ width: "40.625rem", margin: "0 auto" }}>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600, margin: "20px 0 40px", textAlign: "center" }}>리뷰 신청</h2>
      <FormProvider
        submit={async ({ studentId, teacherId, title, prUrl, content }) => {
          // mutation.mutate({
          //   studentId: user.id
          //   teacherId,
          //   title,
          //   prUrl,
          //   content,
          // });
        }}
        validators={reviewRequestValidators}
        css={{ marginTop: "1.25rem", width: "100%" }}
      >
        <Flex css={{ margin: "1.25rem 0 ", width: "100%" }}>
          <InputField
            name="title"
            labelText="타이틀"
            maxLength={STANDARD.REVIEW_REQUEST.TITLE.MAX_LENGTH}
            placeholder={PLACE_HOLDER.REVIEW_REQUEST.TITLE}
            required
          />
        </Flex>
        <Flex css={{ margin: "1.25rem 0 ", width: "100%" }}>
          <InputField
            name="prUrl"
            labelText="리뷰 요청 Pull Request주소"
            maxLength={STANDARD.REVIEW_REQUEST.PR_URL.MAX_LENGTH}
            placeholder={PLACE_HOLDER.REVIEW_REQUEST.PR_URL}
            required
          />
        </Flex>
        <TextareaField
          name="content"
          labelText="본문"
          placeholder={PLACE_HOLDER.REVIEW_REQUEST.CONTENT}
          maxLength={STANDARD.REVIEW_REQUEST.CONTENT.MAX_LENGTH}
          required
          css={{ minHeight: "12.5rem" }}
        />
        <Flex css={{ margin: "1.25rem 0 2.5rem" }}>
          <Button type="submit" themeColor="primary" shape="rounded" css={{ marginLeft: "auto" }}>
            요청
          </Button>
        </Flex>
      </FormProvider>
    </div>
  );
};

export default ReviewRequest;
