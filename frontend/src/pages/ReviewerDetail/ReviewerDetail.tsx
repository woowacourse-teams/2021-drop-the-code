import { useParams } from "react-router-dom";

import ReviewerInfoContainer from "components/Reviewer/ReviewerInfoContainer/ReviewerInfoContainer";

const ReviewerDetail = () => {
  const { reviewerId } = useParams<{ reviewerId: string }>();

  return (
    <>
      <h2>리뷰어 정보</h2>
      <ReviewerInfoContainer reviewerId={Number(reviewerId)} />
    </>
  );
};

export default ReviewerDetail;
