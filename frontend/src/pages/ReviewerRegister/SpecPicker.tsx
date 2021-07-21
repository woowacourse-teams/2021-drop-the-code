import { Dispatch, SetStateAction } from "react";

import CloseSvg from "../../assets/close.svg";
import Button from "../../components/shared/Button/Button";
import { Flex, FlexAlignCenter } from "../../components/shared/Flexbox/Flexbox";
import useLanguageList from "../../hooks/useLanguageList";
import { COLOR } from "../../utils/constants/color";
import { LAYOUT } from "../../utils/constants/size";

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

  return (
    <Flex css={{ flex: "1", flexDirection: "column" }}>
      <Flex css={{ margin: "0.1875rem 0" }}>
        <FlexAlignCenter css={{ width: "5.625rem", fontWeight: 900 }}>언어</FlexAlignCenter>
        <ul css={{ display: "flex" }}>
          {languages?.map(({ language }) => (
            <li key={language.name} css={{ marginRight: "0.375rem" }}>
              <Button
                themeColor="secondary"
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
              <Button
                key={spec}
                themeColor="secondary"
                shape="pill"
                border
                css={{ borderColor: COLOR.INDIGO_500, marginRight: "0.625rem" }}
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
                <CloseSvg width="8px" height="8px" stroke={COLOR.INDIGO_500} />
              </Button>
            ))
          )}
        </FlexAlignCenter>
      </Flex>
    </Flex>
  );
};

export default SpecPicker;
