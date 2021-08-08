import { useEffect, useState } from "react";
import { useQuery } from "react-query";

import { ReviewerSortOption } from "types/reviewer";

import { getReviewerList } from "apis/reviewer";
import { QUERY_KEY } from "utils/constants/key";
import { toURLSearchParams } from "utils/formatter";

import useToastContext from "./useToastContext";

export interface Options {
  filterLanguage: string | null;
  filterSkills: string[];
  filterCareer: number;
  sort: ReviewerSortOption;
}

const useReviewerList = (options: Options) => {
  const [page, setPage] = useState(1);
  const { filterLanguage, filterSkills, filterCareer, sort } = options;

  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_REVIEWER_LIST, ...Object.values(options), page], async () => {
    if (!filterLanguage) return;

    const response = await getReviewerList(
      toURLSearchParams({
        language: filterLanguage,
        skills: filterSkills,
        career: filterCareer,
        size: 5,
        sort,
        page,
      })
    );

    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return { teacherProfiles: [], pageCount: 0 };
    }

    return response.data;
  });

  useEffect(() => {
    setPage(1);
  }, [filterLanguage, filterSkills, filterCareer, sort]);

  return {
    data,
    page,
    setPage,
  };
};

export default useReviewerList;
