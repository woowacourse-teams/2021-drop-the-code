import { useParams } from "react-router-dom";

import ReviewInfoContainer from "components/Review/ReviewInfoContainer/ReviewInfoContainer";

const ReviewDetail = () => {
  const { reviewId } = useParams<{ reviewId: string }>();

  return (
    <>
      <h2>리뷰 상세</h2>

      <ReviewInfoContainer reviewId={Number(reviewId)} />
    </>
  );
};

export default ReviewDetail;
