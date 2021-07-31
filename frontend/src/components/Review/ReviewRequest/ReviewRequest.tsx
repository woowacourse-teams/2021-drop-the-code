import { useMutation, useQueryClient } from "react-query";

import { ReviewRequestFormData } from "types/review";

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
import { PLACE_HOLDER, SUCCESS_MESSAGE } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";
import reviewRequestValidators from "utils/validators/reviewRequestValidators";

interface Props {
  reviewerId: number;
}

const ReviewRequest = ({ reviewerId }: Props) => {
  const { user } = useAuthContext();
  const { revalidate } = useRevalidate();

  const { close } = useModalContext();
  const toast = useToastContext();

  const queryClient = useQueryClient();

  const mutation = useMutation((reviewRequestFormData: ReviewRequestFormData) =>
    revalidate(async () => {
      const response = await requestReview(reviewRequestFormData);

      if (!response.isSuccess) {
        toast(response.error.message);
      } else {
        close();
        toast(SUCCESS_MESSAGE.API.REVIEW.REQUEST);

        queryClient.invalidateQueries("getReview");
      }

      return response;
    })
  );

  if (mutation.isLoading) return <Loading />;

  return (
    <div css={{ width: "40.625rem", margin: "0 auto" }}>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600, margin: "1.25rem 0 2.5rem", textAlign: "center" }}>리뷰 신청</h2>
      <FormProvider
        submit={async ({ title, prUrl, content }) => {
          if (!user) return;

          mutation.mutate({
            studentId: user.id,
            teacherId: reviewerId,
            title,
            prUrl,
            content,
          });
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
          {user && (
            <SubmitButton themeColor="primary" shape="rounded" css={{ marginLeft: "auto" }}>
              요청
            </SubmitButton>
          )}
        </Flex>
      </FormProvider>
    </div>
  );
};

export default ReviewRequest;
