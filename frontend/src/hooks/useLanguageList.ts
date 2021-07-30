import { useQuery } from "react-query";

import { getLanguageList } from "apis/reviewer";
import useToastContext from "hooks/useToastContext";

const useLanguageList = () => {
  const toast = useToastContext();
  const { data: languages } = useQuery("getLanguageList", async () => {
    const response = await getLanguageList();
    if (!response.isSuccess) {
      toast(response.error.message);

      return [];
    }

    return response.data;
  });

  return { languages };
};

export default useLanguageList;
