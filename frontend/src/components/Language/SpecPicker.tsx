import { Dispatch, SetStateAction } from "react";

import styled, { css } from "styled-components";

import CloseSvg from "assets/close.svg";
import Button from "components/shared/Button/Button";
import { Flex, FlexAlignCenter } from "components/shared/Flexbox/Flexbox";
import useLanguageList from "hooks/useLanguageList";
import { COLOR } from "utils/constants/color";

const SpecButton = styled(Button)`
  border: 0.125rem solid;
  font-weight: 900;
  margin: 0.5rem 0.5rem 0 0;
`;

const Close = styled(CloseSvg)<{ $isLanguage: boolean }>`
  width: 0.625rem;
  height: 0.625rem;
  stroke: ${({ $isLanguage, theme }) => css`
    ${$isLanguage ? theme.common.color.primary : COLOR.GREEN_400}
  `};
  stroke-width: 0.125rem;
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
        <Flex css={{ marginBottom: "0.3125rem" }}>
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
        <Flex css={{ flexWrap: "wrap" }}>
          <FlexAlignCenter
            css={{
              width: "100%",
              marginLeft: "5rem",
              minHeight: "3.125rem",
              borderRadius: "0.25rem",
              flexWrap: "wrap",
              padding: "0 0.625rem",
            }}
          >
            {Object.entries(specs).map(([key, value]) =>
              [key, ...value].map((spec) => {
                const isLanguage = Object.keys(specs).includes(spec);
                return (
                  <SpecButton
                    key={spec}
                    themeColor="secondary"
                    shape="pill"
                    css={{
                      borderColor: isLanguage ? COLOR.INDIGO_500 : COLOR.GREEN_400,
                      color: isLanguage ? COLOR.INDIGO_500 : COLOR.GREEN_400,
                    }}
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
                    <Close $isLanguage={isLanguage} />
                  </SpecButton>
                );
              })
            )}
          </FlexAlignCenter>
        </Flex>
      )}
    </Flex>
  );
};

export default SpecPicker;
