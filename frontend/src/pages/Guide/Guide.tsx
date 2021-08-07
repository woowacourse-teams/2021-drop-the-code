import styled from "styled-components";

import * as reviewRequestGuide from "assets/guide/reviewRequest";
import studentImage from "assets/student.svg";
import teacherImage from "assets/teacher.svg";
import Carousel from "components/Carousel/Carousel";
import { FlexCenter, FlexSpaceBetween } from "components/shared/Flexbox/Flexbox";
import useModalContext from "hooks/useModalContext";
import { COLOR } from "utils/constants/color";

const Inner = styled(FlexCenter)`
  max-width: ${({ theme }) => theme.common.layout.lg};
`;

const GuideCardWrapper = styled(FlexSpaceBetween)`
  width: ${({ theme }) => theme.common.layout.lg};
  position: absolute;
  top: 50%;
  left: 50%;
  padding: 6rem;
  transform: translate(-50%, -50%);
`;

const GuideCard = styled(FlexCenter)`
  padding: 6rem;
  flex-direction: column;
  border-radius: ${({ theme }) => theme.common.shape.rounded};
  box-shadow: ${({ theme }) => theme.common.boxShadow.bumped};
  transition: box-shadow 0.3s;
  cursor: pointer;
  :hover {
    box-shadow: ${({ theme }) => theme.common.boxShadow.pressed};
    background-color: ${COLOR.GRAY_100};
  }
`;

const GuideTitle = styled.div`
  font-size: 1.5rem;
  font-weight: 900;
  margin-bottom: 1.25rem;
`;

const StudentImage = styled(studentImage)`
  width: 5rem;
  height: 5rem;
  margin-bottom: 1.25rem;
  stroke: ${({ theme }) => theme.common.color.primary};
  fill: ${({ theme }) => theme.common.color.primary};
`;

const TeacherImage = styled(teacherImage)`
  width: 5rem;
  height: 5rem;
  margin-bottom: 1.25rem;
  stroke: ${({ theme }) => theme.common.color.primary};
  fill: ${({ theme }) => theme.common.color.primary};
`;

const Guide = () => {
  const { open } = useModalContext();

  return (
    <Inner>
      <GuideCardWrapper>
        <GuideCard
          onClick={() => {
            open(
              <Carousel>
                {Object.values(reviewRequestGuide).map((guide) => (
                  <img key={guide} src={guide} alt="리뷰 요청 가이드 이미지" />
                ))}
              </Carousel>
            );
          }}
        >
          <GuideTitle>리뷰 요청 가이드</GuideTitle>
          <StudentImage />
          <div>어떻게 리뷰 받는지 궁금해요!</div>
        </GuideCard>
        <GuideCard
          onClick={() => {
            open(
              <Carousel>
                {Object.values(reviewRequestGuide).map((guide) => (
                  <img key={guide} src={guide} alt="리뷰 요청 가이드 이미지" />
                ))}
              </Carousel>
            );
          }}
        >
          <GuideTitle>리뷰어 가이드</GuideTitle>
          <TeacherImage />
          <div>어떻게 리뷰 하는지 궁금해요!</div>
        </GuideCard>
      </GuideCardWrapper>
    </Inner>
  );
};

export default Guide;
