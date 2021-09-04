import { useState } from "react";
import { useQuery } from "react-query";

import { getChattingList } from "apis/chatting";
import { QUERY_KEY } from "utils/constants/key";

import useRevalidate from "./useRevalidate";
import useToastContext from "./useToastContext";

interface Props {
  id: number;
}

const useChattingList = ({ id }: Props) => {
  const [selectedChatting, setSelectedChatting] = useState(null);

  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_CHATTING_LIST, id], async () => {
    const response = await revalidate(() => getChattingList(id));
    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  return {
    data,
    selectedChatting,
    setSelectedChatting,
  };
};

export default useChattingList;
