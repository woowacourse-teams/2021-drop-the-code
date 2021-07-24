import { Suspense } from "react";
import { useParams } from "react-router-dom";

import Loading from "../../components/Loading/Loading";
import ReviewInfoContainer from "../../components/Review/ReviewInfoContainer/ReviewInfoContainer";
import { LAYOUT } from "../../utils/constants/size";

const ReviewDetail = () => {
  useParams();

  return (
    <main css={{ paddingTop: "6rem", width: "100%", maxWidth: LAYOUT.LG, margin: "0 auto" }}>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600 }}>리뷰 상세</h2>
      <Suspense fallback={<Loading />}>
        <ReviewInfoContainer reviewId={1} />
      </Suspense>
    </main>
  );
};

export default ReviewDetail;
