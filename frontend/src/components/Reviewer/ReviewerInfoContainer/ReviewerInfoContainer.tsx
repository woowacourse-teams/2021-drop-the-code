import { useState } from "react";
import { useQuery } from "react-query";

import { getReviewer } from "../../../apis/reviewer";
import ReviewList from "../../Review/ReviewList/ReviewList";
import Button from "../../shared/Button/Button";
import { Flex, FlexCenter } from "../../shared/Flexbox/Flexbox";
import ReviewerContentBox from "../ReviewerContentBox/ReviewerContentBox";
import ReviewerFloatingBox from "../ReviewerFloatingBox/ReviewerFloatingBox";

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
            {/* <ReviewerContentBox reviewer={data} /> */}
            <FlexCenter css={{ width: "100%" }}>
              <Button themeColor="secondary" hover={false} onClick={() => setOpen(!isOpen)}>
                {isOpen ? "접기" : "리뷰 목록 확인하기"}
              </Button>
            </FlexCenter>
            {isOpen && <ReviewList id={reviewerId} mode="teacher" />}
          </div>
          <div css={{ width: "18.75rem" }}>{/* <ReviewerFloatingBox reviewer={data} /> */}</div>
        </Flex>
      }
    </>
  );
};

export default ReviewerInfoContainer;
