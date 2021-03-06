import styled from "styled-components";
import { Reviewer } from "types/reviewer";

import smBlackLogo from "assets/sm-white-github-logo.png";
import ReviewRequest from "components/Review/ReviewRequest/ReviewRequest";
import Avatar from "components/shared/Avatar/Avatar";
import Button from "components/shared/Button/Button";
import Chip from "components/shared/Chip/Chip";
import { Flex, FlexAlignCenter, FlexCenter } from "components/shared/Flexbox/Flexbox";
import useAuthContext from "hooks/useAuthContext";
import useModalContext from "hooks/useModalContext";
import { COLOR } from "utils/constants/color";
import { ALT } from "utils/constants/message";

const Inner = styled(Flex)`
  flex-direction: column;
  justify-content: space-between;
  position: -webkit-sticky;
  position: sticky;
  top: 12.5rem;
  height: 13rem;
  padding: 1.25rem;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
`;

const ProfileWrapper = styled(Flex)`
  justify-content: space-between;
`;

const AvatarWrapper = styled(Flex)`
  flex-direction: column;
  align-items: center;
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
            height="6.25rem"
            imageUrl={imageUrl}
            width="6.25rem"
            css={{ marginBottom: "0.8rem" }}
            alt={`${name}${ALT.REVIEWER_PROFILE_AVATAR}`}
          />
          <a href={reviewer.githubUrl} target="_blank" rel="noopener noreferrer">
            <FlexAlignCenter>
              <LoginButtonImage src={smBlackLogo} alt={ALT.GITHUB_LOGIN_BUTTON} />
              <p>{name}</p>
            </FlexAlignCenter>
          </a>
        </AvatarWrapper>
        <ChipWrapper>
          <Chip themeColor="career" css={{ fontSize: "0.875rem", marginBottom: "0.3125rem" }}>
            <p>?????? {career}??????</p>
          </Chip>
          <Chip themeColor="count" css={{ fontSize: "0.875rem", marginBottom: "0.3125rem" }}>
            <p>?????? ?????? {sumReviewCount}???</p>
          </Chip>
          <Chip themeColor="averageReview" css={{ fontSize: "0.875rem" }}>
            <p>?????? ?????? {averageReviewTime}???</p>
          </Chip>
        </ChipWrapper>
      </ProfileWrapper>
      <FlexCenter>
        <Button
          themeColor="secondary"
          hover={false}
          css={{ color: COLOR.INDIGO_500, fontWeight: 900 }}
          disabled={!user || user.id === id}
          onClick={() => {
            open(<ReviewRequest reviewerId={id} />);
          }}
        >
          ?????? ????????????
        </Button>
      </FlexCenter>
    </Inner>
  );
};

export default ReviewerFloatingBox;
