import { useParams } from "react-router-dom";

import useModalContext from "../../hooks/useModalContext";
import { LAYOUT } from "../../utils/constants/size";

import ReviewRequest from "./ReviewRequest/ReviewRequest";

const ReviewerDetail = () => {
  const { open } = useModalContext();

  const { reviewerId } = useParams<{ reviewerId: string }>();

  return (
    <main css={{ paddingTop: "6rem", width: "100%", maxWidth: LAYOUT.LG, margin: "0 auto" }}>
      <button
        onClick={() => {
          open(<ReviewRequest teacherId={Number(reviewerId)} />);
        }}
      >
        리뷰요청모달버튼등장
      </button>
    </main>
  );
};

export default ReviewerDetail;
