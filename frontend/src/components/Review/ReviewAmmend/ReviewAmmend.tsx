import { useMutation, useQueryClient } from "react-query";

import { Review, ReviewRequestFormData } from "types/review";

import { requestReview } from "apis/review";
import FormProvider from "components/FormProvider/FormProvider";
import InputField from "components/FormProvider/InputField";
import SubmitButton from "components/FormProvider/SubmitButton";
import TextareaField from "components/FormProvider/TextareaField";
import Loading from "components/Loading/Loading";
import { Flex } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useModalContext from "hooks/useModalContext";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { QUERY_KEY } from "utils/constants/key";
import { PLACE_HOLDER, SUCCESS_MESSAGE } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";
import reviewRequestValidators from "utils/validators/reviewRequestValidators";

interface Props {
  review: Review;
}

const ReviewAmmend = ({ review }: Props) => {
  const { user } = useAuthContext();
  const { revalidate } = useRevalidate();

  const { close } = useModalContext();
  const toast = useToastContext();

  const queryClient = useQueryClient();

  // const mutation = useMutation((reviewRequestFormData: ReviewRequestFormData) =>
  //   revalidate(async () => {
  //     const response = await requestReview(reviewRequestFormData);

  //     if (!response.isSuccess) {
  //       toast(response.error.message);
  //     } else {
  //       close();
  //       toast(SUCCESS_MESSAGE.API.REVIEW.REQUEST);

  //       queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);
  //     }

  //     return response;
  //   })
  // );

  // if (mutation.isLoading) return <Loading />;

  return (
    <div css={{ width: "40.625rem", margin: "0 auto", padding: "1.25rem" }}>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600, margin: "1.25rem 0 2.5rem", textAlign: "center" }}>리뷰 수정</h2>
      <FormProvider
        submit={async ({ title, prUrl, content }) => {
          /*
            request
          */
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
            initialValue={review.title}
            required
          />
        </Flex>
        <Flex css={{ margin: "1.25rem 0 ", width: "100%" }}>
          <InputField
            name="prUrl"
            labelText="리뷰 요청 Pull Request주소"
            maxLength={STANDARD.REVIEW_REQUEST.PR_URL.MAX_LENGTH}
            placeholder={PLACE_HOLDER.REVIEW_REQUEST.PR_URL}
            initialValue={review.prUrl}
            required
          />
        </Flex>
        <TextareaField
          name="content"
          labelText="본문"
          placeholder={PLACE_HOLDER.REVIEW_REQUEST.CONTENT}
          maxLength={STANDARD.REVIEW_REQUEST.CONTENT.MAX_LENGTH}
          initialValue={review.content}
          required
          css={{ minHeight: "12.5rem" }}
        />
        <Flex css={{ margin: "1.25rem 0 2.5rem" }}>
          <SubmitButton themeColor="primary" css={{ marginLeft: "auto" }}>
            수정
          </SubmitButton>
        </Flex>
      </FormProvider>
    </div>
  );
};

export default ReviewAmmend;
