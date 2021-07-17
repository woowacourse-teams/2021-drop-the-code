import { Dispatch, SetStateAction } from "react";

import ReviewerCard from "../../components/Reviewer/ReviewerCard";
import Button from "../../components/shared/Button/Button";
import { FlexCenter } from "../../components/shared/Flexbox/Flexbox";
import useReviewerList, { Options } from "../../hooks/useReviewerList";

interface Props extends Options {
  onSetCurrentPageCount: Dispatch<SetStateAction<number>>;
}

const ReviewerList = ({ onSetCurrentPageCount, ...options }: Props) => {
  const { data } = useReviewerList({
    ...options,
  });

  return (
    <>
      <div css={{ margin: "1.25rem 0 2rem 0" }}>
        {data?.teacherProfiles.map(({ id, ...props }) => (
          <ReviewerCard key={id} id={id} {...props} css={{ marginBottom: "0.625rem" }} />
        ))}
      </div>
      {data && options.currentPageCount < data.pageCount && (
        <FlexCenter css={{ marginBottom: "2.5rem" }}>
          <Button
            themeColor="secondary"
            hover={false}
            css={{ fontWeight: 600 }}
            onClick={() => {
              onSetCurrentPageCount(options.currentPageCount + 1);
            }}
          >
            더보기
          </Button>
        </FlexCenter>
      )}
    </>
  );
};

export default ReviewerList;
