import { useState } from "react";
import { useQuery } from "react-query";

import { Role } from "types/review";

import { getReviewer } from "apis/reviewer";
import ReviewList from "components/Review/ReviewList/ReviewList";
import ReviewerFloatingBox from "components/Reviewer/ReviewerFloatingBox/ReviewerFloatingBox";
import Button from "components/shared/Button/Button";
import ReviewerContentBox from "components/shared/ContentBox/ContentBox";
import { Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";

interface Props {
  reviewerId: number;
}

const ReviewerInfoContainer = ({ reviewerId }: Props) => {
  const [isOpen, setOpen] = useState(false);

  const { data } = useQuery("getReviewer", async () => {
    const response = await getReviewer(reviewerId);

    if (!response.isSuccess) {
      // TODO: 스낵바에 전달
      // response.error.message;
      return;
    }

    return response.data;
  });

  return (
    <>
      {
        <Flex>
          <div css={{ flex: 1, paddingRight: "6.25rem" }}>
            {/* <ContentBox title={reviewer.title} reviewer={data}>
              {reviewer.content}
            </ContentBox> */}
            <FlexCenter css={{ width: "100%" }}>
              <Button themeColor="secondary" hover={false} onClick={() => setOpen(!isOpen)}>
                {isOpen ? "접기" : "리뷰 목록 확인하기"}
              </Button>
            </FlexCenter>
            {isOpen && <ReviewList id={reviewerId} mode={"TEACHER"} />}
          </div>
          <div css={{ width: "18.75rem" }}>{/* <ReviewerFloatingBox reviewer={data} /> */}</div>
        </Flex>
      }
    </>
  );
};

export default ReviewerInfoContainer;
