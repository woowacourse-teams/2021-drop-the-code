import { useQuery } from "react-query";

import { getSingleChatting } from "apis/chatting";
import { QUERY_KEY } from "utils/constants/key";
import { toURLSearchParams } from "utils/formatter";

import useRevalidate from "./useRevalidate";
import useToastContext from "./useToastContext";

const useSingleChatting = (roomId: number | null) => {
  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_SINGLE_CHATTING, roomId], async () => {
    const response = await revalidate(() => getSingleChatting(toURLSearchParams({ roomId })));
    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  return {
    data: data || [],
  };
};

export default useSingleChatting;
