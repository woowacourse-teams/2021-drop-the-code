import noReviewImage from "assets/no-review.png";
import Pagination from "components/Pagination/Pagination";
import ReviewCard from "components/Review/ReviewCard/ReviewCard";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import useReviewList, { Props as useReviewListProps } from "hooks/useReviewList";
import { ALT } from "utils/constants/message";

const ReviewList = ({ id, mode }: useReviewListProps) => {
  const { data, page, setPage } = useReviewList({ id, mode });

  if (data === undefined) return null;

  const { reviews, pageCount } = data;

  if (reviews.length === 0) {
    return (
      <FlexCenter css={{ flexDirection: "column", margin: "5rem 0" }}>
        <img src={noReviewImage} alt={ALT.NO_REVIEW} css={{ width: "18.75rem", marginBottom: "1.25rem" }} />
      </FlexCenter>
    );
  }

  return (
    <>
      <ul>
        {reviews.map((review) => (
          <ReviewCard key={review.id} {...review} />
        ))}
      </ul>
      <FlexCenter>
        <Pagination page={page} setPage={setPage} count={5} maxPage={pageCount} />
      </FlexCenter>
    </>
  );
};

export default ReviewList;
