import { Link } from "react-router-dom";

import styled from "styled-components";
import { Feedback } from "types/reviewer";

import DarkStar from "assets/dark-star.svg";
import LightStar from "assets/light-star.svg";
import Avatar from "components/shared/Avatar/Avatar";
import { Flex, FlexAlignCenter, FlexCenter } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";
import { ALT } from "utils/constants/message";
import { STANDARD } from "utils/constants/standard";

const Item = styled.li`
  display: flex;
  margin-bottom: 1.25rem;
`;

const Content = styled(FlexCenter)`
  flex-direction: column;
  align-items: start;
`;

const Wrapper = styled(Flex)`
  margin-bottom: 0.3125rem;
`;

const Comment = styled.p`
  line-height: 1.25;
  white-space: break-spaces;
`;

const ReviewLink = styled(Link)`
  color: ${COLOR.GRAY_400};
  font-size: 0.9375rem;
`;

const FeedbackCard = ({ id, star, comment, studentProfile }: Feedback) => {
  return (
    <Item>
      <Avatar
        imageUrl={studentProfile.imageUrl}
        width="3rem"
        height="3rem"
        css={{ marginRight: "1.25rem", alignSelf: "center" }}
        alt={`${studentProfile.name}${ALT.REVIEW_REQUESTER_PROFILE_AVATAR}`}
      />
      <Content>
        <FlexAlignCenter css={{ marginBottom: "0.3125rem" }}>
          <Flex css={{ marginRight: "0.625rem" }}>
            {[...Array(STANDARD.REVIEW_FEEDBACK.MAX_GRADE)].map((_, index) =>
              index + 1 <= star ? <LightStar width={15} key={index} /> : <DarkStar width={15} key={index} />
            )}
          </Flex>
          <ReviewLink to={`/review/${id}`}>리뷰 {">"}</ReviewLink>
        </FlexAlignCenter>
        <Wrapper>
          <Comment>{comment}</Comment>
        </Wrapper>
        <div css={{ color: COLOR.GRAY_400, fontSize: "0.9375rem" }}>{studentProfile.name}</div>
      </Content>
    </Item>
  );
};

export default FeedbackCard;
