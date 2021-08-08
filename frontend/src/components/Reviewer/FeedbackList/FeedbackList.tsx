import styled from "styled-components";

import noFeedbackImage from "assets/no-feedback.png";
import Pagination from "components/Pagination/Pagination";
import { FlexCenter } from "components/shared/Flexbox/Flexbox";
import useFeedbackList, { Options } from "hooks/useFeedbackList";
import { ALT } from "utils/constants/message";

import FeedbackCard from "../FeedbackCard/FeedbackCard";

const Inner = styled.ul`
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
`;

const FeedbackList = (options: Options) => {
  const { data, page, setPage } = useFeedbackList(options);

  if (data === undefined) return null;

  const { feedbacks, pageCount } = data;

  if (feedbacks.length === 0)
    return (
      <FlexCenter>
        <img src={noFeedbackImage} alt={ALT.NO_FEEDBACK} css={{ width: "18.75rem", marginBottom: "1.25rem" }} />
      </FlexCenter>
    );

  return (
    <>
      <Inner css={{ padding: "1.25rem" }}>
        {feedbacks.map(({ id, ...props }) => (
          <FeedbackCard key={id} id={id} {...props} />
        ))}
      </Inner>
      <FlexCenter css={{ padding: "3.125rem 0" }}>
        <Pagination page={page} setPage={setPage} count={5} maxPage={pageCount} />
      </FlexCenter>
    </>
  );
};

export default FeedbackList;
