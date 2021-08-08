import styled from "styled-components";

import Pagination from "components/Pagination/Pagination";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import useFeedbackList, { Options } from "hooks/useFeedbackList";

import FeedbackCard from "../FeedbackCard/FeedbackCard";

const Inner = styled.ul`
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
`;

const FeedbackList = (options: Options) => {
  const { data, page, setPage } = useFeedbackList(options);

  if (data === undefined) return null;

  const { feedbacks, pageCount } = data;

  return (
    <>
      <Inner css={{ padding: "20px" }}>
        {feedbacks.map(({ id, ...props }) => {
          <FeedbackCard key={id} id={id} {...props} />;
        })}
      </Inner>
      <FlexCenter css={{ padding: "3.125rem 0" }}>
        <Pagination page={page} setPage={setPage} count={5} maxPage={pageCount} />
      </FlexCenter>
    </>
  );
};

export default FeedbackList;
