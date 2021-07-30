import { Link } from "react-router-dom";

import styled from "styled-components";
import { Reviewer } from "types/reviewer";

import SmBlackLogo from "assets/sm-white-github-logo.png";
import ReviewRequest from "components/Review/ReviewRequest/ReviewRequest";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import Chip from "components/shared/Chip/Chip";
import { Flex, FlexAlignCenter, FlexCenter } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useModalContext from "hooks/useModalContext";
import { ALT } from "utils/constants/message";

const Inner = styled(Flex)`
  flex-direction: column;
  justify-content: space-between;
  position: -webkit-sticky;
  position: sticky;
  top: 18.75rem;
  width: 17.5rem;
  height: 14.375rem;
  padding: 1.25rem;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: 0.4375rem 0.375rem 0.375rem rgb(0 0 0 / 10%);
`;

const ProfileWrapper = styled(Flex)`
  justify-content: space-between;
`;

const AvatarWrapper = styled(Flex)`
  flex-direction: column;
`;

const ChipWrapper = styled(Flex)`
  flex-direction: column;
`;

const LoginButtonImage = styled.img`
  width: 1.25rem;
  height: 1.25rem;
  margin-right: 0.3125rem;
`;

export interface Props {
  reviewer: Reviewer;
}

const ReviewerFloatingBox = ({ reviewer }: Props) => {
  const { open } = useModalContext();

  const { id, imageUrl, name, career, sumReviewCount, averageReviewTime } = reviewer;

  const { user } = useAuthContext();

  return (
    <Inner>
      <ProfileWrapper>
        <AvatarWrapper>
          <Avatar
            alt={ALT.REVIEWER_PROFILE_AVATAR}
            height="6.25rem"
            imageUrl={imageUrl}
            width="6.25rem"
            css={{ marginBottom: "0.3125rem" }}
          />
          <Link to={"/"}>
            <FlexAlignCenter>
              <LoginButtonImage src={SmBlackLogo} alt={ALT.GITHUB_LOGIN_BUTTON} />
              <p>{name}</p>
            </FlexAlignCenter>
          </Link>
        </AvatarWrapper>
        <ChipWrapper>
          <Chip themeColor="career" css={{ fontSize: "0.875rem", marginBottom: "0.3125rem" }}>
            <p>경력 {career}년차</p>
          </Chip>
          <Chip themeColor="count" css={{ fontSize: "0.875rem", marginBottom: "0.3125rem" }}>
            <p>누적 리뷰 {sumReviewCount}회</p>
          </Chip>
          <Chip themeColor="averageReview" css={{ fontSize: "0.875rem" }}>
            <p>평균 답변 {averageReviewTime}일</p>
          </Chip>
        </ChipWrapper>
      </ProfileWrapper>
      {user !== null && user.id !== id && (
        <FlexCenter>
          <Button
            themeColor="secondary"
            hover={false}
            css={{ fontWeight: 900 }}
            onClick={() => {
              open(<ReviewRequest reviewerId={id} />);
            }}
          >
            리뷰 요청하기
          </Button>
        </FlexCenter>
      )}
    </Inner>
  );
};

export default ReviewerFloatingBox;
