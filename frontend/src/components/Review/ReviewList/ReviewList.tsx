import { useQuery } from "react-query";
import { NavLink } from "react-router-dom";

import styled, { css } from "styled-components";
import { Role } from "types/review";

import { getReviewList } from "apis/review";
import noReviewImage from "assets/no-review.png";
import ReviewCard from "components/Review/ReviewCard/ReviewCard";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { QUERY_KEY } from "utils/constants/key";
import { ALT } from "utils/constants/message";
import { PATH } from "utils/constants/path";

const HomeLink = styled(NavLink)`
  background-color: ${({ theme }) => theme.common.color.primary};
  color: ${({ theme }) => theme.common.color.light};
  font-weight: 900;
  border-radius: ${({ theme }) => theme.common.shape.pill};
  padding: 0.625rem 0.9375rem;

  :hover {
    box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
  }

  :active {
    background-color: ${({ theme }) => css`
      ${theme.components.button.primary.activeBg};
    `};
  }

  font-size: 1rem;
`;

interface Props {
  id: number;
  mode: Role;
}

const ReviewList = ({ id, mode }: Props) => {
  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_REVIEW_LIST, id, mode], async () => {
    const response = await revalidate(() => getReviewList(id, mode));
    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return { reviews: [] };
    }

    return response.data;
  });

  if (data?.reviews.length === 0)
    return (
      <FlexCenter css={{ flexDirection: "column", margin: "5rem 0" }}>
        <img src={noReviewImage} alt={ALT.NO_REVIEW} css={{ width: "18.75rem", marginBottom: "1.25rem" }} />
        <HomeLink to={PATH.MAIN}>리뷰어 목록 보러가기</HomeLink>
      </FlexCenter>
    );

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
