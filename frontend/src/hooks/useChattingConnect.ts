import { useQuery } from "react-query";

import { getChattingConnect } from "apis/chatting";
import { QUERY_KEY } from "utils/constants/key";

import useRevalidate from "./useRevalidate";
import useToastContext from "./useToastContext";

interface Props {
  studentId?: number;
  teacherId: number | null;
}

const useChattingConnect = ({ studentId, teacherId }: Props) => {
  const { revalidate } = useRevalidate();
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_CHATTING_CONNECT, studentId, teacherId], async () => {
    if (!studentId || !teacherId) return;

    const response = await revalidate(() => getChattingConnect(studentId, teacherId));
    if (!response.isSuccess) {
      toast(response.error.errorMessage, { type: "error" });

      return;
    }
    return response.data;
  });

  return {
    data,
  };
};

export default useChattingConnect;
