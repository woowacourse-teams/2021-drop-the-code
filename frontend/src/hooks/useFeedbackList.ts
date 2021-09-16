import { useState, useEffect } from "react";
import { useQuery } from "react-query";

import { getFeedbackList } from "apis/reviewer";
import { QUERY_KEY } from "utils/constants/key";
import { toURLSearchParams } from "utils/formatter";

import useToastContext from "./useToastContext";

export interface Options {
  teacherId?: number;
  studentId?: number;
}

const useFeedbackList = (options: Options) => {
  const [page, setPage] = useState(1);
  const { teacherId, studentId } = options;

  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_FEEDBACK_LIST, ...Object.values(options), page], async () => {
    if (!teacherId && !studentId) return;

    const response = await getFeedbackList(
      toURLSearchParams({
        studentId,
        teacherId,
        page,
      })
    );

    if (!response.isSuccess) {
      toast(response.error.errorMessage, { type: "error" });

      return;
    }

    return response.data;
  });

  useEffect(() => {
    setPage(1);
  }, []);

  return {
    data,
    page,
    setPage,
  };
};

export default useFeedbackList;
