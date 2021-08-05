import { languages } from "__mock__/data/languages";
import { mockingToken } from "__mock__/utils/mockingToken";
import { fireEvent, render, screen } from "__mock__/utils/testUtils";

import ReviewerRegister from "./ReviewerRegister";

const { findByRole, getByRole, findByText, findAllByText } = screen;

beforeEach(() => {
  mockingToken();
  render(<ReviewerRegister />);
});

describe("리뷰어 등록 페이지 테스트", () => {
  it("언어 및 기술 목록 조회가 끝나면 언어를 선택할 수 있다.", async () => {
    const languageButton = await findByText(languages[0].language.name);

    expect(languageButton).toBeVisible();
  });

  it("언어를 선택한 뒤 언어에 포함되는 기술을 선택할 수 있고, 선택된 버튼이 표시된다.", async () => {
    const languageButtons = await findAllByText(languages[0].language.name);
    expect(languageButtons).toHaveLength(1);
    fireEvent.click(languageButtons[0]);

    const skillButton = await findByText(languages[0].skills[0].name);
    expect(skillButton).toBeVisible();

    const LanguageButtonsAfterSelect = await findAllByText(languages[0].language.name);
    expect(LanguageButtonsAfterSelect).toHaveLength(2);

    fireEvent.click(LanguageButtonsAfterSelect[1]);
    expect(LanguageButtonsAfterSelect[1]).not.toBeVisible();
  });

  it("타이틀 입력 입력란에 50자 이상 입력하면 유효성 에러 메시지가 출력된다.", async () => {
    const titleInput = await findByRole("textbox", { name: "타이틀" });
    fireEvent.change(titleInput, {
      target: {
        value:
          "50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.50자 넘는 타이틀입니다.",
      },
    });

    const InvalidationTitleMessage = screen.getByText(/50자 이내로 작성해주세요\./i);
    expect(InvalidationTitleMessage).toBeVisible();
  });

  it("경력 입력란에 0에서 50이 아닌 숫자를 입력하면 유효성 에러 메시지가 출력된다.", async () => {
    const careerInput = await findByRole("spinbutton", { name: "경력" });
    fireEvent.change(careerInput, { target: { value: 55 } });

    const inValidCareerMessage = screen.getByText(/0에서 50까지만 입력 가능합니다\./i);
    expect(inValidCareerMessage).toBeVisible();
  });

  it("언어 및 기술 선택, 타이틀, 경력, 소개 입력이 모두 유효한 경우 등록 버튼을 누를 수 있다.", async () => {
    const languageButton = await findByText(languages[0].language.name);
    const titleInput = await findByRole("textbox", { name: "타이틀" });
    const careerInput = getByRole("spinbutton", { name: "경력" });
    const contentTextbox = getByRole("textbox", { name: "소개" });
    const submitButton = getByRole("button", { name: "등록" });

    fireEvent.click(languageButton);
    expect(submitButton).toBeDisabled();
    fireEvent.change(titleInput, { target: { value: "타이틀입니다." } });
    expect(submitButton).toBeDisabled();
    fireEvent.change(careerInput, { target: { value: 3 } });
    expect(submitButton).toBeDisabled();
    fireEvent.change(contentTextbox, { target: { value: "소개입니다." } });
    expect(submitButton).toBeEnabled();
  });
});
