import { Suspense, useEffect, useState } from "react";

import styled, { css } from "styled-components";

import Loading from "../../components/Loading/Loading";
import ReviewList from "../../components/Review/ReviewList/ReviewList";
import useAuthContext from "../../hooks/useAuthContext";
import { ReviewListMode } from "../../types/review";
import { COLOR } from "../../utils/constants/color";
import { LAYOUT } from "../../utils/constants/size";

const Item = styled.li<{ active: boolean }>`
  position: relative;
  ${({ active }) =>
    active &&
    css`
      border-bottom: 1px solid ${COLOR.BLACK};
    `}

  ${({ active }) =>
    active &&
    css`
      :after {
        position: absolute;
        content: "";
        left: 0;
        bottom: -2px;
        width: 100%;
        height: 2px;
        background-color: ${COLOR.BLACK};
      }
    `}
`;

const History = () => {
  const { user } = useAuthContext();

  const [activeTab, setActiveTab] = useState<ReviewListMode | null>(null);

  // const { user } = useAuthContext();

  // const isReviewer = user.role === "teacher";
  const isReviewer = "teacher" === "teacher";

  const myTabs: { name: string; mode: ReviewListMode }[] = isReviewer
    ? [
        { name: "내가 맡은 리뷰", mode: "teacher" },
        { name: "내가 요청한 리뷰", mode: "student" },
      ]
    : [{ name: "내가 요청한 리뷰", mode: "student" }];

  useEffect(() => {
    setActiveTab(myTabs[0].mode);
  }, []);

  return (
    <main css={{ paddingTop: "6rem", width: "100%", maxWidth: LAYOUT.LG, margin: "0 auto" }}>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600 }}>히스토리</h2>
      <ul css={{ display: "flex", borderBottom: `1px solid ${COLOR.GRAY_400}`, marginBottom: "1.25rem" }}>
        {myTabs.map(({ name, mode }) => (
          <Item key={name} active={mode === activeTab}>
            <button
              onClick={() => {
                setActiveTab(mode);
              }}
            >
              {name}
            </button>
          </Item>
        ))}
      </ul>
      <Suspense fallback={<Loading />}>{activeTab && <ReviewList id={1} mode={activeTab} />}</Suspense>
    </main>
  );
};

export default History;
