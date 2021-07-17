import { useInfiniteQuery } from "react-query";

import { getReviewerList } from "../apis/reviewer";
import { ReviwerSortOption } from "../types/reviewer";
import { toURLSearchParams } from "../utils/formatter";

export interface Options {
  filterLanguage: string | null;
  filterSkills: string[];
  filterCareer: number;
  sort: ReviwerSortOption;
}

const useReviewerList = (options: Options) => {
  const { filterLanguage, filterSkills, filterCareer, sort } = options;

  const { data, fetchNextPage } = useInfiniteQuery(
    ["getReviewerList", ...Object.values(options)],
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
        // 스낵바에 전달
        // response.error.message;
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
