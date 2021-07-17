import { useState } from "react";

import { ReviwerSortOption } from "../types/reviewer";

const useReviewerListOptions = () => {
  const [filterLanguage, setFilterLanguage] = useState<string | null>(null);
  const [filterSkills, setFilterSkills] = useState<string[]>([]);
  const [filterCareer, setFilterCareer] = useState(0);
  const [sort, setSort] = useState<ReviwerSortOption>(null);

  return {
    filterLanguage,
    filterSkills,
    filterCareer,
    sort,
    setFilterLanguage,
    setFilterSkills,
    setFilterCareer,
    setSort,
  };
};

export default useReviewerListOptions;
