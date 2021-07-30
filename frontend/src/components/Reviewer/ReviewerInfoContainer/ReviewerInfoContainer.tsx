import { useState } from "react";
import { useQuery } from "react-query";

import { getReviewer } from "apis/reviewer";
import ReviewList from "components/Review/ReviewList/ReviewList";
import ReviewerFloatingBox from "components/Reviewer/ReviewerFloatingBox/ReviewerFloatingBox";
import Button from "components/shared/Button/Button";
import ContentBox from "components/shared/ContentBox/ContentBox";
import { Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";
import useToastContext from "hooks/useToastContext";

interface Props {
  reviewerId: number;
}

const ReviewerInfoContainer = ({ reviewerId }: Props) => {
  const [isOpen, setOpen] = useState(false);
  const toast = useToastContext();

  const { data } = useQuery("getReviewer", async () => {
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
        <Flex>
          <div css={{ flex: 1, paddingRight: "6.25rem" }}>
            <ContentBox title={data.title}>{data.content}</ContentBox>
            <FlexCenter css={{ width: "100%" }}>
              <Button themeColor="secondary" hover={false} onClick={() => setOpen(!isOpen)}>
                {isOpen ? "접기" : "리뷰 목록 확인하기"}
              </Button>
            </FlexCenter>
            {isOpen && <ReviewList id={reviewerId} mode={"TEACHER"} />}
          </div>
          <div css={{ width: "18.75rem" }}>
            <ReviewerFloatingBox reviewer={data} />
          </div>
        </Flex>
      )}
    </>
  );
};

export default ReviewerInfoContainer;
