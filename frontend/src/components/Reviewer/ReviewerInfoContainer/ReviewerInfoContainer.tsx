import { Suspense } from "react";
import { useQuery } from "react-query";

import { getReviewer } from "apis/reviewer";
import Loading from "components/Loading/Loading";
import ReviewList from "components/Review/ReviewList/ReviewList";
import ReviewerFloatingBox from "components/Reviewer/ReviewerFloatingBox/ReviewerFloatingBox";
import ContentBox from "components/shared/ContentBox/ContentBox";
import { Flex } from "components/shared/Flexbox/Flexbox";
import useToastContext from "hooks/useToastContext";
import { QUERY_KEY } from "utils/constants/key";

import FeedbackList from "../FeedbackList/FeedbackList";

interface Props {
  reviewerId: number;
}

const ReviewerInfoContainer = ({ reviewerId }: Props) => {
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_REVIEWER, reviewerId], async () => {
    const response = await getReviewer(reviewerId);

    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  return (
    <>
      {data && (
        <Flex css={{ marginBottom: "3.125rem" }}>
          <div css={{ width: "18.75rem", marginRight: "6.25rem" }}>
            <ReviewerFloatingBox reviewer={data} />
          </div>
          <div css={{ flex: 1 }}>
            <ContentBox title={data.title} css={{ minHeight: "31.25rem", marginBottom: "1.875rem" }}>
              {data.content}
            </ContentBox>
            <h3>리뷰 목록</h3>
            <Suspense fallback={<Loading />}>
              <ReviewList id={reviewerId} mode="TEACHER" />
            </Suspense>
            <h3>피드백 목록</h3>
            <Suspense fallback={<Loading />}>
              <FeedbackList teacherId={reviewerId} />
            </Suspense>
          </div>
        </Flex>
      )}
    </>
  );
};

export default ReviewerInfoContainer;
