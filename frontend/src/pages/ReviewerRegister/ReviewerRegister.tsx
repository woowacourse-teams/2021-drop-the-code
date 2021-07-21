import { Suspense, useState } from "react";

import Loading from "../../components/Loading/Loading";
import Button from "../../components/shared/Button/Button";
import { Flex } from "../../components/shared/Flexbox/Flexbox";
import Input from "../../components/shared/Input/Input";
import Textarea from "../../components/shared/Textarea/Textarea";

import SpecPicker from "./SpecPicker";

/*

spec: {java:["spring"] , javascript:["react","vue"]}

*/
interface Specs {
  [language: string]: string[];
}

const ReviewerRegister = () => {
  const [filterLanguage, setFilterLanguage] = useState<string | null>(null);
  const [specs, setSpecs] = useState<Specs>({});

  return (
    <div css={{ width: "100%" }}>
      <h2>리뷰어 등록</h2>
      <p css={{ fontSize: "16px", marginBottom: "10px" }}>스펙</p>
      <Suspense fallback={<Loading />}>
        <SpecPicker
          filterLanguage={filterLanguage}
          specs={specs}
          onSetFilterLanguage={setFilterLanguage}
          onSetSpecs={setSpecs}
        />
      </Suspense>
      <form css={{ marginTop: "20px", width: "100%" }}>
        <Flex css={{ marginBottom: "20px", width: "100%" }}>
          <div css={{ flexGrow: 5, marginRight: "20px" }}>
            <Input
              errorMessage="30자 이내로 작성해주세요."
              labelText="타이틀"
              placeholder="리뷰어 목록에 나타날 타이틀입니다. 본인을 나타낼 수 있는 한 줄 소개를 작성해주세요."
            />
          </div>
          <div css={{ flexGrow: 1 }}>
            <Input
              errorMessage="0 에서 50값만 가능합니다."
              labelText="경력"
              type="number"
              min={0}
              max={50}
              placeholder="3년차"
            />
          </div>
        </Flex>
        <Textarea
          errorMessage="5,000자 이내로 입력해주세요."
          labelText="소개"
          placeholder={`안녕하세요. ㅇㅇㅇ입니다.\nㅇㅇ 회사에서 프론트엔드로 3년간 근무했습니다.\n리뷰 요청을 주실 때, 중점적으로 코드리뷰 받고 싶은 부분을 기재해주시면 좋습니다.\n\n· 경력: ~~\n· 이력: ~~\n· 블로그: ~~\n· 링크: ~~`}
          css={{ minHeight: "500px" }}
        />
        <Flex css={{ margin: "1.25rem 0 2.5rem" }}>
          <Button type="submit" themeColor="primary" shape="rounded" css={{ marginLeft: "auto" }}>
            등록
          </Button>
        </Flex>
      </form>
    </div>
  );
};

export default ReviewerRegister;
