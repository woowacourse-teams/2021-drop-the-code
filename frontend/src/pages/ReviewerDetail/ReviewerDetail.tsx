import { useParams } from "react-router-dom";

import ReviewRequest from "../../components/Reviewer/ReviewRequest/ReviewRequest";
import useModalContext from "../../hooks/useModalContext";
import { LAYOUT } from "../../utils/constants/size";

const ReviewerDetail = () => {
  const { open } = useModalContext();

  const { reviewerId } = useParams<{ reviewerId: string }>();

  return (
    <main css={{ paddingTop: "6rem", width: "100%", maxWidth: LAYOUT.LG, margin: "0 auto" }}>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600 }}>리뷰어 정보</h2>
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
