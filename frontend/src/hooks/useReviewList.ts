import { useState } from "react";
import { useQuery } from "react-query";

import { Role } from "types/review";

import { getReviewList } from "apis/review";
import { QUERY_KEY } from "utils/constants/key";
import { toURLSearchParams } from "utils/formatter";

import useRevalidate from "./useRevalidate";
import useToastContext from "./useToastContext";

export interface Props {
  id: number;
  mode: Role;
}

const useReviewList = ({ id, mode }: Props) => {
  const [page, setPage] = useState(1);

  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_REVIEW_LIST, id, mode, page], async () => {
    const response = await revalidate(() =>
      getReviewList(id, mode, toURLSearchParams({ page, size: 5, sort: ["createdAt,desc"] }))
    );
    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  return {
    data,
    page,
    setPage,
  };
};

export default useReviewList;
