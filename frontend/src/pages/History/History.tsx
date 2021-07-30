import { Suspense, useEffect, useState } from "react";
import { Redirect } from "react-router-dom";

import styled, { css } from "styled-components";
import { Role } from "types/review";

import Loading from "components/Loading/Loading";
import ReviewList from "components/Review/ReviewList/ReviewList";
import useAuthContext from "hooks/useAuthContext";
import { COLOR } from "utils/constants/color";
import { PATH } from "utils/constants/path";

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

  const [activeTab, setActiveTab] = useState<Role | null>(null);

  if (!user) return <Redirect to={PATH.MAIN} />;

  const isReviewer = user.role === "TEACHER";

  const myTabs: { name: string; mode: Role }[] = isReviewer
    ? [
        { name: "내가 맡은 리뷰", mode: "TEACHER" },
        { name: "내가 요청한 리뷰", mode: "STUDENT" },
      ]
    : [{ name: "내가 요청한 리뷰", mode: "STUDENT" }];

  useEffect(() => {
    setActiveTab(myTabs[0].mode);
  }, []);

  return (
    <>
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
      <Suspense fallback={<Loading />}>{activeTab && <ReviewList id={user.id} mode={activeTab} />}</Suspense>
    </>
  );
};

export default History;
