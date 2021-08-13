import { useMutation, useQuery, useQueryClient } from "react-query";
import { useHistory } from "react-router-dom";

import { ReviewFeedback, ReviewRequestFormData } from "types/review";

import {
  acceptReview,
  cancelReview,
  completeReview,
  denyReview,
  editReview,
  finishReview,
  getReview,
} from "apis/review";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { QUERY_KEY } from "utils/constants/key";
import { SUCCESS_MESSAGE } from "utils/constants/message";
import { PATH } from "utils/constants/path";
import { PROGRESS } from "utils/constants/progress";

import useAuthContext from "./useAuthContext";

const useReview = (reviewId: number) => {
  const queryClient = useQueryClient();
  const { revalidate } = useRevalidate();
  const { user } = useAuthContext();
  const toast = useToastContext();

  const history = useHistory();

  const { data: review } = useQuery([QUERY_KEY.GET_REVIEW, reviewId], async () => {
    const response = await getReview(reviewId);

    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      history.push(PATH.MAIN);
      return;
    }

    return response.data;
  });

  const cancel = useMutation(() => {
    return revalidate(async () => {
      const response = await cancelReview(reviewId);

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });
      } else {
        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);

        toast(SUCCESS_MESSAGE.API.REVIEW.DELETE);
      }

      return response;
    });
  });

  const deny = useMutation(() => {
    return revalidate(async () => {
      const response = await denyReview(reviewId);

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });
      } else {
        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);

        toast(SUCCESS_MESSAGE.API.REVIEW.DENY);
      }

      return response;
    });
  });

  const accept = useMutation(() => {
    return revalidate(async () => {
      const response = await acceptReview(reviewId);

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });
      } else {
        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);

        toast(SUCCESS_MESSAGE.API.REVIEW.ACCEPT);
      }

      return response;
    });
  });

  const edit = useMutation((reviewRequestFormData: ReviewRequestFormData) => {
    return revalidate(async () => {
      const response = await editReview(reviewId, reviewRequestFormData);

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });
      } else {
        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);

        toast(SUCCESS_MESSAGE.API.REVIEW.EDIT);
      }

      return response;
    });
  });

  const complete = useMutation(() => {
    return revalidate(async () => {
      const response = await completeReview(reviewId);

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });
      } else {
        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);

        toast(SUCCESS_MESSAGE.API.REVIEW.COMPLETE);
      }

      return response;
    });
  });

  const finish = useMutation((reviewFeedback: ReviewFeedback) => {
    return revalidate(async () => {
      const response = await finishReview(reviewId, reviewFeedback);

      if (!response.isSuccess) {
        toast(response.error.message, { type: "error" });
      } else {
        queryClient.invalidateQueries(QUERY_KEY.GET_REVIEW);

        toast(SUCCESS_MESSAGE.API.REVIEW.FINISH);
      }

      return response;
    });
  });

  if (!review) {
    return {
      state: null,
      computed: null,
      mutation: {
        cancel: cancel.mutate,
        deny: deny.mutate,
        accept: accept.mutate,
        edit: edit.mutate,
        complete: complete.mutate,
        finish: finish.mutate,
      },
    };
  }

  const currentProgress = review.progress;

  const isPending = currentProgress === "PENDING";
  const isDenied = currentProgress === "DENIED";
  const isOnGoing = currentProgress === "ON_GOING";
  const isTeacherCompleted = currentProgress === "TEACHER_COMPLETED";
  const isFinished = currentProgress === "FINISHED";

  const student = review.studentProfile;
  const teacher = review.teacherProfile;

  const isStudent = student.id === user?.id;
  const isTeacher = teacher.id === user?.id;
  const isAnonymous = !(isStudent || isTeacher);

  const nextProgress =
    currentProgress === "ON_GOING" ? "리뷰 완료" : currentProgress === "TEACHER_COMPLETED" ? "리뷰 종료" : null;

  return {
    state: { review, user },
    computed: {
      status: { isPending, isDenied, isOnGoing, isTeacherCompleted, isFinished },
      role: { isStudent, isTeacher, isAnonymous },
      progress: PROGRESS[currentProgress],
      nextProgress,
    },
    mutation: {
      cancel: cancel.mutate,
      deny: deny.mutate,
      accept: accept.mutate,
      edit: edit.mutate,
      complete: complete.mutate,
      finish: finish.mutate,
    },
  };
};

export default useReview;
