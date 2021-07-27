import { useState } from "react";

import { ReviewerSortOption } from "../types/reviewer";

const useReviewerListOptions = () => {
  const [filterLanguage, setFilterLanguage] = useState<string | null>(null);
  const [filterSkills, setFilterSkills] = useState<string[]>([]);
  const [filterCareer, setFilterCareer] = useState(0);
  const [sort, setSort] = useState<ReviewerSortOption>(null);

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
