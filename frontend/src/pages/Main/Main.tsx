import { Suspense } from "react";

import { ReviewerSortOption } from "types/reviewer";

import CareerPicker from "components/CareerPicker/CareerPicker";
import LanguageList from "components/Language/LanguageList";
import Loading from "components/Loading/Loading";
import MenuItemButton from "components/MenuItemButton/MenuItemButton";
import ReviewerList from "components/Reviewer/ReviewerList/ReviewerList";
import { Flex, FlexEnd } from "components/shared/Flexbox/Flexbox";
import Select from "components/shared/Select/Select";
import useReviewerListOptions from "hooks/useReviewerListOptions";
import { COLOR } from "utils/constants/color";

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
    <>
      <h2 css={{ fontSize: "1.25rem", fontWeight: 600 }}>리뷰어 찾기</h2>
      <Flex css={{ flexDirection: "column", width: "100%" }}>
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
                shape="pill"
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
                {filterCareer === 0 ? "경력 무관" : `경력 ${filterCareer}년 이상`}
              </MenuItemButton>
            </div>
          </FlexEnd>
        </Flex>
      </Flex>
      <div css={{ paddingTop: "1.25rem", borderTop: `1px solid ${COLOR.GRAY_300}` }}>
        <Select onChange={({ target: { value } }) => setSort(value as ReviewerSortOption)}>
          <option value="career,desc">경력 많은순</option>
          <option value="averageReviewTime,asc">리뷰 빠른순</option>
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
    </>
  );
};

export default Main;
