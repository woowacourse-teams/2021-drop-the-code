import { useQuery } from "react-query";

import { Role } from "types/review";

import { getReviewList } from "apis/review";
import ReviewCard from "components/Review/ReviewCard/ReviewCard";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";

interface Props {
  id: number;
  mode: Role;
}

const ReviewList = ({ id, mode }: Props) => {
  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const { data } = useQuery(["getReviewList", mode], async () => {
    const response = await revalidate(() => getReviewList(id, mode));
    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

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
