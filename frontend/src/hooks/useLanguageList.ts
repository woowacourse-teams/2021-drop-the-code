import { Dispatch, SetStateAction } from "react";
import { useQuery } from "react-query";

import { getLanguageList } from "../apis/reviewer";

export interface FileterLanguageState {
  filterLanguage: string | null;
  onSetFilterLanguage: Dispatch<SetStateAction<string | null>>;
}

const useLanguageList = () => {
  const { data: languages } = useQuery(["getLanguageList"], async () => {
    const response = await getLanguageList();
    if (!response.isSuccess) {
      // 스낵바에 전달
      // response.error.message;
      return [];
    }

    return response.data;
  });

  return { languages };
};

export default useLanguageList;
