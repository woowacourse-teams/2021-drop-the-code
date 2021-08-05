import { useInfiniteQuery } from "react-query";

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
  const { filterLanguage, filterSkills, filterCareer, sort } = options;

  const toast = useToastContext();

  const { data, fetchNextPage } = useInfiniteQuery(
    [QUERY_KEY.GET_REVIEWER_LIST, ...Object.values(options)],
    async ({ pageParam = 1 }) => {
      if (!filterLanguage) return;

      const response = await getReviewerList(
        toURLSearchParams({
          language: filterLanguage,
          skills: filterSkills,
          career: filterCareer,
          sort,
          page: pageParam,
        })
      );

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });

        return { teacherProfiles: [], pageCount: 0 };
      }

      return response.data;
    }
  );

  return {
    data,
    fetchNextPage,
  };
};

export default useReviewerList;
