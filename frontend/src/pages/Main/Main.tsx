import { Suspense } from "react";

import CareerPicker from "../../components/CareerPicker/CareerPicker";
import Loading from "../../components/Loading/Loading";
import MenuItemButton from "../../components/MenuItemButton/MenuItemButton";
import { Flex, FlexEnd } from "../../components/shared/Flexbox/Flexbox";
import Select from "../../components/shared/Select/Select";
import useReviewerListOptions from "../../hooks/useReviewerListOptions";
import { ReviwerSortOption } from "../../types/reviewer";
import { COLOR } from "../../utils/constants/color";
import { LAYOUT } from "../../utils/constants/size";

import LanguageList from "./LanguageList";
import ReviewerList from "./ReviewerList";

const Main = () => {
  const {
    filterLanguage,
    filterSkills,
    filterCareer,
    sort,
    setFilterLanguage,
    setFilterSkills,
    setFilterCareer,
    setSort,
  } = useReviewerListOptions();

  return (
    <main css={{ maxWidth: LAYOUT.LG, margin: "0 auto" }}>
      <Flex css={{ flexDirection: "column", width: "100%" }}>
        <h2 css={{ fontSize: "1.25rem", fontWeight: 600, margin: "1.25rem 0" }}>리뷰어 찾기</h2>
        <Flex>
          <Suspense fallback={<Loading />}>
            <LanguageList
              filterLanguage={filterLanguage}
              filterSkills={filterSkills}
              onSetFilterLanguage={setFilterLanguage}
              onSetFilterSkills={setFilterSkills}
            />
          </Suspense>
          <FlexEnd css={{ flexDirection: "column" }}>
            <div css={{ marginBottom: "0.625rem" }}>
              <MenuItemButton
                themeColor="secondary"
                border
                contents={(close) => (
                  <CareerPicker
                    filterCareer={filterCareer}
                    onSetFilterCareer={(career) => {
                      setFilterCareer(career);
                      close();
                    }}
                  />
                )}
              >
                경력
              </MenuItemButton>
            </div>
          </FlexEnd>
        </Flex>
      </Flex>
      <div css={{ paddingTop: "1.25rem", borderTop: `1px solid ${COLOR.GRAY_300}` }}>
        <Select onChange={({ target: { value } }) => setSort(value as ReviwerSortOption)}>
          <option value="career,desc">경력 많은순</option>
          <option value="averageReviewTime,desc">리뷰 빠른순</option>
          <option value="sumReviewCount,desc">리뷰 많은순</option>
          {/* <option id="3">추천순</option> */}
        </Select>
      </div>
      <Suspense fallback={<Loading />}>
        <ReviewerList
          filterLanguage={filterLanguage}
          filterSkills={filterSkills}
          filterCareer={filterCareer}
          sort={sort}
        />
      </Suspense>
    </main>
  );
};

export default Main;
