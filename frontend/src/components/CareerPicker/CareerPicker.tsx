import { useState } from "react";

import styled from "styled-components";

import { COLOR } from "../../utils/constants/color";
import Button from "../shared/Button/Button";
import { FlexCenter, FlexEnd } from "../shared/Flexbox/Flexbox";

export interface Props {
  filterCareer: number;
  onSetFilterCareer: (career: number) => void;
}

const Inner = styled(FlexCenter)`
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
    margin-top: -10px;
    width: 15px;
    height: 20px;
    background: ${({ theme }) => theme.common.color.primary};
    border-radius: 5px;
    cursor: pointer;
    -webkit-appearance: none;
  }
`;

const DataList = styled.datalist`
  display: flex;
  justify-content: space-between;
  width: 92%;
  padding: 0 2px;
  margin-top: 0.3125rem;

  > option {
    font-size: 14px;
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
        max={5}
        step={1}
        list="tickmarks"
        onChange={({ target }) => {
          const { valueAsNumber } = target;

          setCareer(valueAsNumber);
        }}
      />
      <DataList id="tickmarks">
        <option>0</option>
        <option>1</option>
        <option>2</option>
        <option>3</option>
        <option>4</option>
        <option>5</option>
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
