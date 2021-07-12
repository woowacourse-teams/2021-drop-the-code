import { reviewers } from "../../__mock__/reviewers";
import FloatingMenuButton from "../../components/FloatingMenuButton/FloatingMenuButton";
import ReviewerCard from "../../components/Reviewer/ReviewerCard";
import Button from "../../components/shared/Button/Button";
import { Flex, FlexEnd, FlexAlignCenter, FlexCenter } from "../../components/shared/Flexbox/Flexbox";
import Select from "../../components/shared/Select/Select";
import { COLOR } from "../../utils/constants/color";
import { LAYOUT } from "../../utils/constants/size";

const Main = () => {
  return (
    <main css={{ maxWidth: LAYOUT.LG, margin: "0 auto" }}>
      <Flex css={{ flexDirection: "column", width: "100%" }}>
        <h2 css={{ fontSize: "1.25rem", fontWeight: 600, margin: "1.25rem 0" }}>리뷰어 찾기</h2>
        <Flex>
          <Flex css={{ flex: "1", flexDirection: "column" }}>
            <Flex css={{ margin: "0.3125rem 0" }}>
              <FlexAlignCenter css={{ width: "5rem", fontWeight: 500 }}>언어</FlexAlignCenter>
              <ul css={{ display: "flex" }}>
                <li css={{ marginRight: "0.375rem" }}>
                  <Button themeColor="transParent">java</Button>
                </li>
                <li>
                  <Button active themeColor="transParent">
                    javascript
                  </Button>
                </li>
              </ul>
            </Flex>
            <Flex css={{ margin: "0.625rem 0" }}>
              <FlexAlignCenter css={{ width: "5rem", fontWeight: 500 }}>기술 스택</FlexAlignCenter>
              <ul css={{ display: "flex" }}>
                <li>
                  <Button active themeColor="transParent" css={{ marginRight: "0.1875rem" }}>
                    react
                  </Button>
                </li>
                <li>
                  <Button themeColor="transParent">vue</Button>
                </li>
                <li>
                  <Button themeColor="transParent">angular</Button>
                </li>
              </ul>
            </Flex>
          </Flex>
          <FlexEnd css={{ flexDirection: "column" }}>
            <div css={{ marginBottom: "0.625rem" }}>
              <FloatingMenuButton>경력</FloatingMenuButton>
            </div>
          </FlexEnd>
        </Flex>
      </Flex>
      <div css={{ paddingTop: "1.25rem", borderTop: `1px solid ${COLOR.GRAY_300}` }}>
        <Select defaultValue="1">
          <option id={"1"}>추천순</option>
          <option id={"2"}>리뷰 빠른순</option>
          <option id={"3"}>경력 많은순</option>
        </Select>
      </div>
      <div css={{ margin: "1.25rem 0 2rem 0" }}>
        {reviewers.map(({ id, avatarUrl, career, reviewCount, averageResponseTime, title, languages, skills }) => (
          <ReviewerCard
            key={id}
            id={id}
            avatarUrl={avatarUrl}
            career={career}
            reviewCount={reviewCount}
            averageResponseTime={averageResponseTime}
            title={title}
            languages={languages}
            skills={skills}
            css={{ marginBottom: "10px" }}
          />
        ))}
      </div>
      <FlexCenter css={{ marginBottom: "1.25rem" }}>
        <Button themeColor="transParent" hover={false} css={{ fontWeight: 600 }}>
          더보기
        </Button>
      </FlexCenter>
    </main>
  );
};

export default Main;
