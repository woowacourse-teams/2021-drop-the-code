import ReviewerCard from "components/Reviewer/ReviewerCard/ReviewerCard";
import Button from "components/shared/Button/Button";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import useReviewerList, { Options } from "hooks/useReviewerList";

type Props = Options;

const ReviewerList = ({ ...options }: Props) => {
  const { data, fetchNextPage } = useReviewerList({
    ...options,
  });

  const pageLength = data?.pages.length || 0;

  return (
    <>
      <div css={{ margin: "1.25rem 0 2rem 0" }}>
        {data?.pages.map((page) =>
          page?.teacherProfiles.map(({ id, ...props }) => (
            <ReviewerCard key={id} id={id} {...props} css={{ marginBottom: "0.625rem" }} />
          ))
        )}
      </div>
      {pageLength < (data?.pages[0]?.pageCount || 0) && (
        <FlexCenter css={{ marginBottom: "3.75rem" }}>
          <Button
            themeColor="secondary"
            hover={false}
            css={{ fontWeight: 600 }}
            onClick={() => {
              fetchNextPage({ pageParam: pageLength + 1 });
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
