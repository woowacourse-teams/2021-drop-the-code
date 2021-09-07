import { useState } from "react";

import styled from "styled-components";

import Button from "components/shared/Button/Button";
import { FlexCenter, FlexEnd } from "components/shared/Flexbox/Flexbox";
import { COLOR } from "utils/constants/color";

export interface Props {
  filterCareer: number;
  onSetFilterCareer: (career: number) => void;
}

const Inner = styled(FlexCenter)`
  padding: 1.875rem;
  width: 15.625rem;
  height: 8.125rem;
  flex-direction: column;
`;

const Career = styled.input`
  width: 92%;
  cursor: pointer;
  background-color: secondary;
  -webkit-appearance: none;

  ::-webkit-slider-runnable-track {
    background: #000;
    height: 1px;
    cursor: pointer;
  }
  ::-webkit-slider-thumb {
    margin-top: -0.625rem;
    width: 0.9375rem;
    height: 1.25rem;
    background: ${({ theme }) => theme.common.color.primary};
    border-radius: 0.3125rem;
    cursor: pointer;
    -webkit-appearance: none;
  }
`;

const DataList = styled.datalist`
  display: flex;
  justify-content: space-between;
  width: 95%;
  padding: 0 1px;
  margin-top: 0.3125rem;

  > option {
    display: flex;
    justify-content: center;
    font-size: 14px;
    width: 22px;
    color: ${COLOR.GRAY_600};
  }
`;

const ResultWrapper = styled(FlexEnd)`
  align-self: flex-end;
  align-items: center;
  margin: 2.5rem 0.625rem 0 0;
`;

const CareerText = styled.p`
  font-size: 16px;
  margin-right: 10px;
  color: ${COLOR.GRAY_600};
`;

const CareerPicker = ({ filterCareer, onSetFilterCareer }: Props) => {
  const [career, setCareer] = useState(filterCareer);

  return (
    <Inner>
      <Career
        type="range"
        value={career}
        min={0}
        max={15}
        step={3}
        list="tickmarks"
        onChange={({ target }) => {
          const { valueAsNumber } = target;

          setCareer(valueAsNumber);
        }}
      />
      <DataList id="tickmarks">
        {[0, 3, 6, 9, 12, 15].map((val) => (
          <option key={val}>{val}</option>
        ))}
      </DataList>
      <ResultWrapper>
        <CareerText>{career ? `${career}년 이상` : "경력 무관"}</CareerText>
        <Button
          type="button"
          themeColor="primary"
          shape="pill"
          onClick={() => {
            onSetFilterCareer(career);
          }}
        >
          저장
        </Button>
      </ResultWrapper>
    </Inner>
  );
};

export default CareerPicker;
