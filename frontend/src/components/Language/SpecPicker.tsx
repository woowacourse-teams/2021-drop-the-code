import { Dispatch, SetStateAction } from "react";

import styled from "styled-components";

import CloseSvg from "../../assets/close.svg";
import useLanguageList from "../../hooks/useLanguageList";
import { COLOR } from "../../utils/constants/color";
import { LAYOUT } from "../../utils/constants/size";
import Button from "../shared/Button/Button";
import { Flex, FlexAlignCenter } from "../shared/Flexbox/Flexbox";

const SpecButton = styled(Button)`
  color: ${({ theme }) => theme.common.color.primary};
  font-weight: 900;
  border: 2px solid ${({ theme }) => theme.common.color.primary};
  margin-right: 0.625rem;
`;

const Close = styled(CloseSvg)`
  width: 10px;
  height: 10px;
  stroke: ${({ theme }) => theme.common.color.primary};
  stroke-width: 2px;
`;

interface Specs {
  [language: string]: string[];
}

interface Props {
  filterLanguage: string | null;
  specs: Specs;
  onSetFilterLanguage: Dispatch<SetStateAction<string | null>>;
  onSetSpecs: Dispatch<SetStateAction<Specs>>;
}

const SpecPicker = ({ filterLanguage, specs, onSetFilterLanguage, onSetSpecs }: Props) => {
  const { languages } = useLanguageList();
  // TODO 텅빈화면
  if (!languages || languages?.length === 0) return <></>;

  const isSpecsExist = Object.keys(specs).length > 0;

  return (
    <Flex css={{ flex: "1", flexDirection: "column" }}>
      <Flex css={{ margin: "0.1875rem 0" }}>
        <FlexAlignCenter css={{ width: "5.625rem", fontWeight: 900 }}>언어</FlexAlignCenter>
        <ul css={{ display: "flex" }}>
          {languages?.map(({ language }) => (
            <li key={language.name} css={{ marginRight: "0.375rem" }}>
              <Button
                themeColor="secondary"
                type="button"
                active={isSpecsExist && filterLanguage === language.name}
                onClick={() => {
                  onSetFilterLanguage(language.name);
                  if (Object.keys(specs).includes(language.name)) return;

                  onSetSpecs({ ...specs, [language.name]: [] });
                }}
              >
                {language.name}
              </Button>
            </li>
          ))}
        </ul>
      </Flex>
      {isSpecsExist && (
        <Flex css={{ marginBottom: "5px" }}>
          <FlexAlignCenter css={{ width: "5.625rem", fontWeight: 900 }}>기술 스택</FlexAlignCenter>
          <ul css={{ display: "flex" }}>
            {languages
              ?.find(({ language }) => {
                return language.name === filterLanguage;
              })
              ?.skills.map((skill) => (
                <li key={skill.name}>
                  <Button
                    type="button"
                    themeColor="secondary"
                    css={{ marginRight: "0.1875rem" }}
                    onClick={() => {
                      if (!filterLanguage) return;

                      if (!specs[filterLanguage]) {
                        onSetSpecs({ ...specs, [filterLanguage]: [skill.name] });

                        return;
                      }

                      onSetSpecs({ ...specs, [filterLanguage]: [...new Set([...specs[filterLanguage], skill.name])] });
                    }}
                  >
                    {skill.name}
                  </Button>
                </li>
              ))}
          </ul>
        </Flex>
      )}
      {isSpecsExist && (
        <Flex css={{ width: LAYOUT.LG, flexWrap: "wrap" }}>
          <FlexAlignCenter
            css={{
              width: "100%",
              minHeight: "50px",
              border: `1px solid ${COLOR.GRAY_500}`,
              borderRadius: "4px",
              flexWrap: "wrap",
              padding: "0 10px",
            }}
          >
            {Object.entries(specs).map(([key, value]) =>
              [key, ...value].map((spec) => (
                <SpecButton
                  key={spec}
                  themeColor="secondary"
                  shape="pill"
                  onClick={() => {
                    if (!filterLanguage) return;

                    if (Object.keys(specs).includes(spec)) {
                      // eslint-disable-next-line @typescript-eslint/no-unused-vars
                      const { [spec]: deletedSpec, ...newSpecs } = specs;

                      onSetSpecs(newSpecs);

                      return;
                    }

                    const [parent] = Object.entries(specs)
                      .filter(([_, value]) => value.includes(spec))
                      .map(([key, _]) => key);

                    const newSkills = specs[parent].filter((skill) => skill !== spec);

                    onSetSpecs({ ...specs, [parent]: newSkills });
                  }}
                >
                  {`${spec} `}
                  <Close />
                </SpecButton>
              ))
            )}
          </FlexAlignCenter>
        </Flex>
      )}
    </Flex>
  );
};

export default SpecPicker;
