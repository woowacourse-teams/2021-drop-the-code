import { useState } from "react";

import styled, { css } from "styled-components";

import NextCarouselArrow from "assets/next-carousel-arrow.svg";
import PrevCarouselArrow from "assets/prev-carousel-arrow.svg";
import { Flex, FlexCenter } from "components/shared/Flexbox/Flexbox";

export interface Props {
  children: JSX.Element[];
}

const Inner = styled(FlexCenter)`
  width: 1050px;
  position: relative;
`;

const ImageWrapper = styled(Flex)<{ page: number }>`
  width: 960px;
  overflow: hidden;

  img {
    width: 960px;
    border-radius: ${({ theme }) => theme.common.shape.rounded};
    object-fit: cover;
    transform: ${({ page }) =>
      css`
        translateX(calc(${-960 * page}px))
      `};
    transition: transform 0.5s;
    user-select: none;
  }

  svg {
    width: 40px;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    cursor: pointer;
    border-radius: ${({ theme }) => theme.common.shape.circle};
    box-shadow: ${({ theme }) => theme.common.boxShadow.bumped};
    transition: box-shadow 0.3s;
    :hover {
      box-shadow: ${({ theme }) => theme.common.boxShadow.pressed};
    }
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
