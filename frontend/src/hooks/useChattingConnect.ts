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
    const response = await revalidate(() => getChattingConnect(studentId, teacherId));
    if (!response.isSuccess) {
      console.log("에러", response.error);
      toast(response.error.message, { type: "error" });

      return;
    }
    console.log("성공", response.data);
    return response.data;
  });

  return {
    data,
  };
};

export default useChattingConnect;
