import { Suspense } from "react";
import { useParams } from "react-router-dom";

import Loading from "components/Loading/Loading";
import ReviewerInfoContainer from "components/Reviewer/ReviewerInfoContainer/ReviewerInfoContainer";

const ReviewerDetail = () => {
  const { reviewerId } = useParams<{ reviewerId: string }>();

  return (
    <>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600 }}>리뷰어 정보</h2>
      <Suspense fallback={<Loading />}>
        <ReviewerInfoContainer reviewerId={Number(reviewerId)} />
      </Suspense>
    </>
  );
};

export default ReviewerDetail;
