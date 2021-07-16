import { Dispatch, SetStateAction, useEffect, useState } from "react";

import { techSpec as mockTechSpec } from "../../__mock__/filter";
import Button from "../../components/shared/Button/Button";
import { Flex, FlexAlignCenter } from "../../components/shared/Flexbox/Flexbox";
import { TechSpec } from "../../types/reviewer";

interface Props {
  filterLanguage: string | null;
  filterSkills: string[];
  onSetFilterLanguage: Dispatch<SetStateAction<string | null>>;
  onSetFilterSkills: Dispatch<SetStateAction<string[]>>;
}

const TechSpecMenu = ({ filterLanguage, filterSkills, onSetFilterLanguage, onSetFilterSkills }: Props) => {
  // useQuery techSpec

  const [techSpec, setTechSpec] = useState<TechSpec[]>(mockTechSpec);

  if (techSpec.length === 0) return <div>텅빈화면</div>;

  useEffect(() => {
    onSetFilterLanguage(techSpec[0].language);
  }, []);

  // const techSpec = [];
  return (
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
                  onSetFilterLanguage(language);
                  onSetFilterSkills([]);
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
                      onSetFilterSkills(filterSkills.filter((filterSkill) => filterSkill !== skill));

                      return;
                    }

                    onSetFilterSkills([...filterSkills, skill]);
                  }}
                >
                  {skill}
                </Button>
              </li>
            ))}
        </ul>
      </Flex>
    </Flex>
  );
};

export default TechSpecMenu;
