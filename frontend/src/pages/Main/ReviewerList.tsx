import { Dispatch, SetStateAction } from "react";

import { reviewers } from "../../__mock__/reviewers";
import ReviewerCard from "../../components/Reviewer/ReviewerCard";
import Button from "../../components/shared/Button/Button";
import { FlexCenter } from "../../components/shared/Flexbox/Flexbox";
import { ReviwerSortOption } from "../../types/reviewer";

interface Props {
  filterLanguage: string | null;
  filterSkills: string[];
  filterCareer: number;
  pageCount: number;
  sort: ReviwerSortOption;
  onSetPageCount: Dispatch<SetStateAction<number>>;
}

const ReviewerList = ({ filterLanguage, filterSkills, filterCareer, pageCount, sort, onSetPageCount }: Props) => {
  // useQuery()

  return (
    <>
      <div css={{ margin: "1.25rem 0 2rem 0" }}>
        {reviewers.map(({ id, imageUrl, career, reviewCount, averageResponseTime, title, techSpec }) => (
          <ReviewerCard
            key={id}
            id={id}
            imageUrl={imageUrl}
            career={career}
            reviewCount={reviewCount}
            averageResponseTime={averageResponseTime}
            title={title}
            techSpec={techSpec}
            css={{ marginBottom: "0.625rem" }}
          />
        ))}
      </div>
      <FlexCenter css={{ marginBottom: "2.5rem" }}>
        <Button
          themeColor="secondary"
          hover={false}
          css={{ fontWeight: 600 }}
          onClick={() => {
            onSetPageCount(pageCount + 1);
          }}
        >
          더보기
        </Button>
      </FlexCenter>
    </>
  );
};

export default ReviewerList;
