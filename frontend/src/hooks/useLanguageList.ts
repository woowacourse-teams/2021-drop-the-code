import { useQuery } from "react-query";

import { getLanguageList } from "../apis/reviewer";

const useLanguageList = () => {
  const { data: languages } = useQuery(["getLanguageList"], async () => {
    const response = await getLanguageList();
    if (!response.isSuccess) {
      // TODO:스낵바에 전달
      // response.error.message;
      return [];
    }

    return response.data;
  });

  return { languages };
};

export default useLanguageList;
