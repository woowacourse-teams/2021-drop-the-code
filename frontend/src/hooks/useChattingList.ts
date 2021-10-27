import { useQuery } from "react-query";

import { getChattingList } from "apis/chatting";
import { QUERY_KEY } from "utils/constants/key";

import useRevalidate from "./useRevalidate";
import useToastContext from "./useToastContext";

const useChattingList = (id?: number) => {
  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_CHATTING_LIST, id], async () => {
    if (!id) return;

    const response = await revalidate(() => getChattingList(id));
    if (!response.isSuccess) {
      toast(response.error.errorMessage, { type: "error" });

      return;
    }

    return response.data;
  });

  return {
    chattingList: data || [],
  };
};

export default useChattingList;
