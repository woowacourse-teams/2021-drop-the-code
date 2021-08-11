import { useQuery } from "react-query";

import { getLanguageList } from "apis/reviewer";
import useToastContext from "hooks/useToastContext";
import { QUERY_KEY } from "utils/constants/key";

const useLanguageList = () => {
  const toast = useToastContext();
  const { isLoading, data: languages } = useQuery(QUERY_KEY.GET_LANGUAGE_LIST, async () => {
    const response = await getLanguageList();
    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  return { languages, isLoading };
};

export default useLanguageList;
