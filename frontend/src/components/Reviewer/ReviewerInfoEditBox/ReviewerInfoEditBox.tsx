import { useMutation, useQuery, useQueryClient } from "react-query";

import styled from "styled-components";

import { deleteReviewer } from "apis/reviewer";
import { getReviewer } from "apis/reviewer";
import Confirm from "components/Confirm/Confirm";
import Loading from "components/Loading/Loading";
import Button from "components/shared/Button/Button";
import { Flex, FlexEnd } from "components/shared/Flexbox/Flexbox";
import useModalContext from "hooks/useModalContext";
import useRevalidate from "hooks/useRevalidate";
import useToastContext from "hooks/useToastContext";
import { COLOR } from "utils/constants/color";
import { QUERY_KEY } from "utils/constants/key";
import { CONFIRM, SUCCESS_MESSAGE } from "utils/constants/message";

import ReviewerEdit from "../ReviewerEdit/ReviewerEdit";

const Inner = styled(Flex)`
  flex-direction: column;
  width: 55%;
  margin-bottom: 2.5rem;
  padding: 1.25rem;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
  line-height: 1.5625rem;
`;

const ContentTitle = styled.div`
  min-width: 1.875rem;
  margin-right: 1.25rem;
  color: ${COLOR.GRAY_600};
  font-weight: 700;
`;

interface Props {
  reviewerId: number;
}

const ReviewerInfoEditBox = ({ reviewerId }: Props) => {
  const { open } = useModalContext();
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_REVIEWER, reviewerId], async () => {
    const response = await getReviewer(reviewerId);

    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  const { revalidate } = useRevalidate();
  const queryClient = useQueryClient();

  const deleteReviewerMutation = useMutation(() => {
    return revalidate(async () => {
      const response = await deleteReviewer();

      if (response.isSuccess) {
        toast(SUCCESS_MESSAGE.API.REVIEWER.DELETE);
        queryClient.invalidateQueries(QUERY_KEY.CHECK_MEMBER);
      }

      return response;
    });
  });

  if (deleteReviewerMutation.isLoading) return <Loading />;

  return (
    <>
      {data && (
        <Inner>
          <div css={{ fontWeight: 700, marginBottom: "0.9375rem" }}>등록한 리뷰어 정보</div>
          <Flex css={{ flexDirection: "column" }}>
            <Flex>
              <ContentTitle>제목</ContentTitle>
              <div>{data.title}</div>
            </Flex>
            <Flex>
              <ContentTitle>본문</ContentTitle>
              <div css={{ width: "70%", whiteSpace: "nowrap", overflow: "hidden", textOverflow: "ellipsis" }}>
                {data.content}
              </div>
            </Flex>
            <Flex>
              <ContentTitle>스택</ContentTitle>
              <p css={{ paddingRight: "0.625rem" }}>
                {`언어 - ${[...data.techSpec.languages.map((language) => language.name)].join(", ")}`}
              </p>
              <p>{`기술 - ${[...data.techSpec.skills.map((skill) => skill.name)].join(", ")}`}</p>
            </Flex>
          </Flex>
          <Flex>
            <ContentTitle>경력</ContentTitle>
            <div>{data.career}년</div>
          </Flex>
          <FlexEnd>
            <Button themeColor="secondary" onClick={() => open(<ReviewerEdit reviewer={data} />)}>
              수정
            </Button>
            <Button
              themeColor="secondary"
              onClick={() => {
                open(
                  <Confirm
                    title={CONFIRM.REVIEWER.DELETE}
                    onConfirm={() => {
                      deleteReviewerMutation.mutate();
                    }}
                  />
                );
              }}
            >
              삭제
            </Button>
          </FlexEnd>
        </Inner>
      )}
    </>
  );
};

export default ReviewerInfoEditBox;
