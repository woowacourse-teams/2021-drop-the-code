import { Dispatch, SetStateAction } from "react";

import Button from "../../components/shared/Button/Button";
import { Flex, FlexAlignCenter } from "../../components/shared/Flexbox/Flexbox";
import useLanguageList from "../../hooks/useLanguageList";

interface Props {
  filterSkills: string[];
  onSetFilterSkills: Dispatch<SetStateAction<string[]>>;
  filterLanguage: string | null;
  onSetFilterLanguage: Dispatch<SetStateAction<string | null>>;
}

const LanguageList = ({ filterLanguage, filterSkills, onSetFilterLanguage, onSetFilterSkills }: Props) => {
  const { languages } = useLanguageList();
  // TODO 텅빈화면
  if (languages?.length === 0) return <></>;

  // 초기 메인페이지에서 필터 언어를 첫번째로 선택
  if (languages && !filterLanguage) onSetFilterLanguage(languages[0].language.name);

  return (
    <Flex css={{ flex: "1", flexDirection: "column" }}>
      <Flex css={{ margin: "0.3125rem 0" }}>
        <FlexAlignCenter css={{ width: "5rem", fontWeight: 500 }}>언어</FlexAlignCenter>
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
      <Flex css={{ margin: "0.625rem 0" }}>
        <FlexAlignCenter css={{ width: "5rem", fontWeight: 500 }}>기술 스택</FlexAlignCenter>
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
