import { Dispatch, SetStateAction } from "react";

import { nanoid } from "nanoid";
import styled from "styled-components";

import Button from "components/shared/Button/Button";

interface Props {
  maxPage: number;
  page: number;
  count: number;
  setPage: Dispatch<SetStateAction<number>>;
}

const Ol = styled.ol`
  display: flex;
  padding: 3.125rem 0;
`;

const Li = styled.li`
  margin: 0 0.3125rem;
`;

const getButtonList = (cur: number, count: number, maxPage: number) => {
  const start =
    cur + Math.floor(count / 2) <= count
      ? 1
      : maxPage <= cur + Math.floor(count / 2)
      ? maxPage - count
      : cur - Math.floor(count / 2);

  return [...Array(count)].map((_, index) => start + index).filter((val) => 1 <= val && val <= maxPage);
};

const Pagination = ({ page, count, maxPage, setPage }: Props) => {
  const ButtonList = getButtonList(page, count, maxPage);

  return (
    <Ol>
      {ButtonList.map((val) => (
        <Li key={nanoid()}>
          <Button
            onClick={() => {
              setPage(val);
            }}
            active={page === val}
          >
            {val}
          </Button>
        </Li>
      ))}
    </Ol>
  );
};

export default Pagination;
