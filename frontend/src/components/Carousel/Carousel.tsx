import { useState } from "react";

import styled, { css } from "styled-components";

import NextCarouselArrow from "assets/next-carousel-arrow.svg";
import PrevCarouselArrow from "assets/prev-carousel-arrow.svg";
import { Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

export interface Props {
  children: HTMLImageElement[];
}

const Inner = styled(FlexCenter)`
  width: 800px;
  position: relative;
`;

const ImageWrapper = styled(Flex)<{ page: number }>`
  width: 700px;
  overflow: hidden;

  img {
    width: 700px;
    border-radius: ${({ theme }) => theme.common.shape.rounded};
    object-fit: cover;
    transform: ${({ page }) =>
      css`
        translateX(calc(${-700 * page}px))
      `};
    transition: transform 0.5s;
  }

  svg {
    width: 40px;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    cursor: pointer;
    border: 1px solid ${COLOR.GRAY_500};
    border-radius: ${({ theme }) => theme.common.shape.circle};
    box-shadow: ${({ theme }) => theme.common.boxShadow.primary};
  }
`;

const PrevButton = styled(PrevCarouselArrow)`
  left: -15px;
`;

const NextButton = styled(NextCarouselArrow)`
  right: -15px;
`;

const Carousel = ({ children }: Props) => {
  const [page, setPage] = useState(0);

  const maxLength = children.length;

  const showPrev = () => {
    setPage(page > 0 ? page - 1 : page);
  };

  const showNext = () => {
    setPage(page < maxLength - 1 ? page + 1 : page);
  };

  return (
    <Inner>
      <ImageWrapper page={page}>
        {children}
        {page > 0 && (
          <PrevButton
            onClick={() => {
              showPrev();
            }}
          />
        )}
        {page < maxLength - 1 && (
          <NextButton
            onClick={() => {
              showNext();
            }}
          />
        )}
      </ImageWrapper>
    </Inner>
  );
};

export default Carousel;
