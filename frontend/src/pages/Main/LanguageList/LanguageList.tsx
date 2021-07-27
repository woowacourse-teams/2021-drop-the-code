import { Dispatch, SetStateAction, useEffect } from "react";

import Button from "../../../components/shared/Button/Button";
import { Flex, FlexAlignCenter } from "../../../components/shared/Flexbox/Flexbox";
import useLanguageList from "../../../hooks/useLanguageList";

interface Props {
  filterLanguage: string | null;
  filterSkills: string[];
  onSetFilterLanguage: Dispatch<SetStateAction<string | null>>;
  onSetFilterSkills: Dispatch<SetStateAction<string[]>>;
}

const LanguageList = ({ filterLanguage, filterSkills, onSetFilterLanguage, onSetFilterSkills }: Props) => {
  const { languages } = useLanguageList();
  // TODO: 텅빈화면
  if (languages?.length === 0) return <></>;

  // 초기 메인페이지에서 필터 언어를 첫번째로 선택

  useEffect(() => {
    if (languages && !filterLanguage) onSetFilterLanguage(languages[0].language.name);
  }, []);

  return (
    <Flex css={{ flex: "1", flexDirection: "column" }}>
      <Flex css={{ margin: "0.1875rem 0" }}>
        <FlexAlignCenter css={{ width: "5.625rem", fontWeight: 900 }}>언어</FlexAlignCenter>
        <ul css={{ display: "flex" }}>
          {languages?.map(({ language }) => (
            <li key={language.name} css={{ marginRight: "0.375rem" }}>
              <Button
                themeColor="secondary"
                active={language.name === filterLanguage}
                onClick={() => {
                  onSetFilterLanguage(language.name);
                  onSetFilterSkills([]);
                }}
              >
                {language.name}
              </Button>
            </li>
          ))}
        </ul>
      </Flex>
      <Flex css={{ marginBottom: "15px" }}>
        <FlexAlignCenter css={{ width: "5.625rem", fontWeight: 900 }}>기술 스택</FlexAlignCenter>
        <ul css={{ display: "flex" }}>
          {languages
            ?.find(({ language }) => {
              return language.name === filterLanguage;
            })
            ?.skills.map((skill) => (
              <li key={skill.name}>
                <Button
                  active={filterSkills.includes(skill.name)}
                  themeColor="secondary"
                  css={{ marginRight: "0.1875rem" }}
                  onClick={() => {
                    // 필터링 목록에 포함된 경우 제외
                    if (filterSkills.includes(skill.name)) {
                      onSetFilterSkills(filterSkills.filter((filterSkill) => filterSkill !== skill.name));
                      return;
                    }

                    onSetFilterSkills([...filterSkills, skill.name]);
                  }}
                >
                  {skill.name}
                </Button>
              </li>
            ))}
        </ul>
      </Flex>
    </Flex>
  );
};

export default LanguageList;
