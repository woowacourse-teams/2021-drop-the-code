import { useQuery } from "react-query";

import { getReviewList } from "../../../apis/review";
import { ReviewListMode } from "../../../types/review";
import ReviewCard from "../ReviewCard/ReviewCard";

interface Props {
  id: number;
  mode: ReviewListMode;
}

const ReviewList = ({ id, mode }: Props) => {
  const { data } = useQuery(["getReviewList", mode], async () => {
    // const response = await getReviewList(user.id, mode);
    const response = await getReviewList(id, mode);
    if (!response.isSuccess) {
      // TODO:스낵바에 전달
      // response.error.message;
      return { reviews: [] };
    }

    return response.data;
  });

  console.log(data);

  return (
    <>
      {data && (
        <ul>
          {data.reviews.map((review) => (
            <ReviewCard key={review.id} {...review} />
          ))}
        </ul>
      )}
    </>
  );
};

export default ReviewList;
