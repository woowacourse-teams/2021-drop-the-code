import { useQuery } from "react-query";

import { getChattingConnectToken } from "apis/chatting";
import { QUERY_KEY } from "utils/constants/key";

import useRevalidate from "./useRevalidate";
import useToastContext from "./useToastContext";

const useChattingConnectToken = () => {
  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_CHATTING_CONNECT_TOKEN], async () => {
    const response = await revalidate(() => getChattingConnectToken());

    if (!response.isSuccess) {
      toast(response.error.errorMessage, { type: "error" });

      return;
    }

    return response.data;
  });

  return {
    connectToken: data?.accessToken || "",
  };
};

export default useChattingConnectToken;
