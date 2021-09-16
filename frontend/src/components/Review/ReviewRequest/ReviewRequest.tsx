import { useMutation, useQueryClient } from "react-query";

import styled from "styled-components";
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
import { COLOR } from "utils/constants/color";
import { QUERY_KEY } from "utils/constants/key";
import { PLACE_HOLDER, SUCCESS_MESSAGE } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";
import reviewRequestValidators from "utils/validators/reviewRequestValidators";

const Inner = styled.div`
  background-color: ${COLOR.WHITE};
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  flex-direction: column;
  justify-content: space-evenly;
  width: 40.625rem;
  margin: 0 auto;
  padding: 1.25rem;
`;

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
        toast(response.error.errorMessage, { type: "error" });
      } else {
        close();
        toast(SUCCESS_MESSAGE.API.REVIEW.REQUEST);

        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);
      }

      return response;
    })
  );

  if (mutation.isLoading || !user) return <Loading />;

  return (
    <Inner>
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
            labelText="Pull Request주소"
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
          {<SubmitButton css={{ marginLeft: "auto" }}>요청</SubmitButton>}
        </Flex>
      </FormProvider>
    </Inner>
  );
};

export default ReviewRequest;
