import { Suspense, useState } from "react";
import { useQuery } from "react-query";

import styled from "styled-components";

import { getReviewer } from "apis/reviewer";
import DownArrowSvg from "assets/down-arrow.svg";
import Loading from "components/Loading/Loading";
import ReviewList from "components/Review/ReviewList/ReviewList";
import ReviewerFloatingBox from "components/Reviewer/ReviewerFloatingBox/ReviewerFloatingBox";
import Button from "components/shared/Button/Button";
import ContentBox from "components/shared/ContentBox/ContentBox";
import { Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";
import useToastContext from "hooks/useToastContext";
import { COLOR } from "utils/constants/color";
import { QUERY_KEY } from "utils/constants/key";

import FeedbackList from "../FeedbackList/FeedbackList";

interface Props {
  reviewerId: number;
}

const DownArrow = styled(DownArrowSvg)`
  width: 1rem;
  height: 1rem;
  fill: ${({ theme }) => theme.common.color.primary};
`;

const ReviewerInfoContainer = ({ reviewerId }: Props) => {
  const [isReviewListOpen, setReviewListOpen] = useState(false);
  const [isFeedbackListOpen, setFeedbackListOpen] = useState(false);
  const toast = useToastContext();

  const { data } = useQuery([QUERY_KEY.GET_REVIEWER, reviewerId], async () => {
    const response = await getReviewer(reviewerId);

    if (!response.isSuccess) {
      toast(response.error.message, { type: "error" });

      return;
    }

    return response.data;
  });

  return (
    <>
      {data && (
        <Flex css={{ marginBottom: "3.125rem" }}>
          <div css={{ width: "18.75rem", marginRight: "6.25rem" }}>
            <ReviewerFloatingBox reviewer={data} />
          </div>
          <div css={{ flex: 1 }}>
            <ContentBox title={data.title} css={{ minHeight: "31.25rem" }}>
              {data.content}
            </ContentBox>
            <FlexCenter css={{ width: "100%", margin: "2.5rem 0" }}>
              <Button
                themeColor="secondary"
                hover={false}
                onClick={() => setReviewListOpen(!isReviewListOpen)}
                css={{ color: COLOR.INDIGO_500, fontWeight: 900 }}
              >
                {isReviewListOpen ? (
                  <FlexCenter>
                    접기 <DownArrow css={{ transform: "rotate(180deg)" }} />
                  </FlexCenter>
                ) : (
                  <FlexCenter>
                    리뷰 목록 확인하기 <DownArrow />
                  </FlexCenter>
                )}
              </Button>
            </FlexCenter>
            {isReviewListOpen && <ReviewList id={reviewerId} mode="TEACHER" />}
            <FlexCenter css={{ width: "100%", margin: "2.5rem 0" }}>
              <Button
                themeColor="secondary"
                hover={false}
                onClick={() => setFeedbackListOpen(!isFeedbackListOpen)}
                css={{ color: COLOR.INDIGO_500, fontWeight: 900 }}
              >
                {isFeedbackListOpen ? (
                  <FlexCenter>
                    접기 <DownArrow css={{ transform: "rotate(180deg)" }} />
                  </FlexCenter>
                ) : (
                  <FlexCenter>
                    피드백 목록 확인하기 <DownArrow />
                  </FlexCenter>
                )}
              </Button>
            </FlexCenter>
            {isFeedbackListOpen && <FeedbackList teacherId={reviewerId} />}
          </div>
        </Flex>
      )}
    </>
  );
};

export default ReviewerInfoContainer;
