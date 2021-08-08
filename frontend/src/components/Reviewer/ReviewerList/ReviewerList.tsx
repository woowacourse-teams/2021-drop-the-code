import Pagination from "components/Pagination/Pagination";
import ReviewerCard from "components/Reviewer/ReviewerCard/ReviewerCard";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import useReviewerList, { Options } from "hooks/useReviewerList";

const ReviewerList = ({ ...options }: Options) => {
  const { data, page, setPage } = useReviewerList({
    ...options,
  });

  if (data === undefined) return null;

  const { teacherProfiles, pageCount } = data;

  if (teacherProfiles.length === 0) {
    return (
      <FlexCenter css={{ flexDirection: "column", margin: "5rem 0" }}>
        리뷰어가 없습니다.. 리뷰어로 등록해주세요~
      </FlexCenter>
    );
  }

  return (
    <>
      <ul css={{ margin: "1.25rem 0 2rem 0" }}>
        {teacherProfiles.map(({ id, ...props }) => (
          <ReviewerCard key={id} id={id} {...props} css={{ marginBottom: "0.625rem" }} />
        ))}
      </ul>
      <FlexCenter css={{ padding: "3.125rem 0" }}>
        <Pagination page={page} setPage={setPage} count={5} maxPage={pageCount} />
      </FlexCenter>
    </>
  );
};

export default ReviewerList;
