import { Suspense } from "react";
import { useParams } from "react-router-dom";

import Loading from "components/Loading/Loading";
import ReviewInfoContainer from "components/Review/ReviewInfoContainer/ReviewInfoContainer";

const ReviewDetail = () => {
  const { reviewId } = useParams<{ reviewId: string }>();

  return (
    <>
      <h2>리뷰 상세</h2>
      <Suspense fallback={<Loading />}>
        <ReviewInfoContainer reviewId={Number(reviewId)} />
      </Suspense>
    </>
  );
};

export default ReviewDetail;
