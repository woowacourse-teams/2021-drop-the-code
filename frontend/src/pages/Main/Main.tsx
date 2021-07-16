import { useEffect, useState } from "react";

import { techSpec as mockTechSpec } from "../../__mock__/filter";
import { reviewers } from "../../__mock__/reviewers";
import CareerPicker from "../../components/CareerPicker/CareerPicker";
import MenuItemButton from "../../components/MenuItemButton/MenuItemButton";
import ReviewerCard from "../../components/Reviewer/ReviewerCard";
import Button from "../../components/shared/Button/Button";
import { Flex, FlexEnd, FlexAlignCenter, FlexCenter } from "../../components/shared/Flexbox/Flexbox";
import Select from "../../components/shared/Select/Select";
import { TechSpec } from "../../types/reviewer";
import { COLOR } from "../../utils/constants/color";
import { LAYOUT } from "../../utils/constants/size";

const Main = () => {
  const [filterLanguage, setFilterLanguage] = useState<string | null>(null);
  const [filterSkills, setFilterSkills] = useState<string[]>([]);
  const [filterCareer, setFilterCareer] = useState(0);

  // TODO: api 요청 받아서 처리하기
  const [techSpec, setTechSpec] = useState<TechSpec[]>(mockTechSpec);

  useEffect(() => {
    if (filterSkills.length === 0) {
      const skills = techSpec.find(({ language }) => language === filterLanguage)?.skills;

      // await api(language, skills)
      // setTechSpec()

      return;
    }

    // await api
    // setTechSpec()
  }, [filterLanguage, filterSkills, filterCareer]);

  useEffect(() => {
    if (techSpec.length === 0) return;

    setFilterLanguage(techSpec[0].language);
  }, [techSpec]);

  return (
    <main css={{ maxWidth: LAYOUT.LG, margin: "0 auto" }}>
      <Flex css={{ flexDirection: "column", width: "100%" }}>
        <h2 css={{ fontSize: "1.25rem", fontWeight: 600, margin: "1.25rem 0" }}>리뷰어 찾기</h2>
        <Flex>
          <Flex css={{ flex: "1", flexDirection: "column" }}>
            <Flex css={{ margin: "0.3125rem 0" }}>
              <FlexAlignCenter css={{ width: "5rem", fontWeight: 500 }}>언어</FlexAlignCenter>
              <ul css={{ display: "flex" }}>
                {techSpec.map(({ language }) => (
                  <li key={language} css={{ marginRight: "0.375rem" }}>
                    <Button
                      themeColor="secondary"
                      active={language === filterLanguage}
                      onClick={() => {
                        setFilterLanguage(language);
                        setFilterSkills([]);
                      }}
                    >
                      {language}
                    </Button>
                  </li>
                ))}
              </ul>
            </Flex>
            <Flex css={{ margin: "0.625rem 0" }}>
              <FlexAlignCenter css={{ width: "5rem", fontWeight: 500 }}>기술 스택</FlexAlignCenter>
              <ul css={{ display: "flex" }}>
                {techSpec
                  .find(({ language }) => language === filterLanguage)
                  ?.skills.map((skill) => (
                    <li key={skill}>
                      <Button
                        active={filterSkills.includes(skill)}
                        themeColor="secondary"
                        css={{ marginRight: "0.1875rem" }}
                        onClick={() => {
                          if (filterSkills.includes(skill)) {
                            setFilterSkills(filterSkills.filter((filterSkill) => filterSkill !== skill));

                            return;
                          }

                          setFilterSkills([...filterSkills, skill]);
                        }}
                      >
                        {skill}
                      </Button>
                    </li>
                  ))}
              </ul>
            </Flex>
          </Flex>
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
        <Select defaultValue="1">
          <option id={"1"}>추천순</option>
          <option id={"2"}>리뷰 빠른순</option>
          <option id={"3"}>경력 많은순</option>
        </Select>
      </div>
      <div css={{ margin: "1.25rem 0 2rem 0" }}>
        {reviewers.map(({ id, imageUrl, career, reviewCount, averageResponseTime, title, techSpec }) => (
          <ReviewerCard
            key={id}
            id={id}
            imageUrl={imageUrl}
            career={career}
            reviewCount={reviewCount}
            averageResponseTime={averageResponseTime}
            title={title}
            techSpec={techSpec}
            css={{ marginBottom: "0.625rem" }}
          />
        ))}
      </div>
      <FlexCenter css={{ marginBottom: "2.5rem" }}>
        <Button themeColor="secondary" hover={false} css={{ fontWeight: 600 }}>
          더보기
        </Button>
      </FlexCenter>
    </main>
  );
};

export default Main;
