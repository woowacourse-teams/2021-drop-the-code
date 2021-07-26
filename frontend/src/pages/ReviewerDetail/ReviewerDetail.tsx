import { Suspense } from "react";
import { useParams } from "react-router-dom";

import Loading from "components/Loading/Loading";
import ReviewerInfoContainer from "components/Reviewer/ReviewerInfoContainer/ReviewerInfoContainer";
import { LAYOUT } from "utils/constants/size";

const ReviewerDetail = () => {
  const { reviewerId } = useParams<{ reviewerId: string }>();

  return (
    <main css={{ paddingTop: "6rem", width: "100%", maxWidth: LAYOUT.LG, margin: "0 auto" }}>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600 }}>리뷰어 정보</h2>
      <Suspense fallback={<Loading />}>
        <ReviewerInfoContainer reviewerId={Number(reviewerId)} />
      </Suspense>
    </main>
  );
};

export default ReviewerDetail;
