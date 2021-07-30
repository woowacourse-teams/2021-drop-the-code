import { useQuery } from "react-query";

import { Role } from "types/review";

import { getReviewList } from "apis/review";
import ReviewCard from "components/Review/ReviewCard/ReviewCard";
import useRevalidate from "hooks/useRevalidate";

interface Props {
  id: number;
  mode: Role;
}

const ReviewList = ({ id, mode }: Props) => {
  const { revalidate } = useRevalidate();

  const { data } = useQuery(["getReviewList", mode], async () => {
    // const response = await getReviewList(user.id, mode);
    const response = await revalidate(() => getReviewList(id, mode));
    if (!response.isSuccess) {
      // TODO:스낵바에 전달
      // response.error.message;
      return { reviews: [] };
    }

    return response.data;
  });

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
