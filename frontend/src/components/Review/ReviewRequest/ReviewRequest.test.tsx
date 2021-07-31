import { mockingStudentAuth } from "__mock__/utils/mockingAuth";
import { mockingToken } from "__mock__/utils/mockingToken";
import { render, screen, fireEvent } from "__mock__/utils/testUtils";
import ReviewRequest from "components/Review/ReviewRequest/ReviewRequest";

const { getByRole, findByRole } = screen;

beforeEach(() => {
  mockingToken();
  mockingStudentAuth();
  render(<ReviewRequest reviewerId={1} />);
});

describe("리뷰 요청 컴포넌트 테스트", () => {
  it("제목이 입력되지 않은 경우 리뷰를 요청할 수 없다.", async () => {
    const prUrlInput = await findByRole("textbox", { name: "리뷰 요청 Pull Request주소" });
    const contentTextarea = getByRole("textbox", { name: "본문" });
    const submitButton = getByRole("button", { name: "요청" });

    fireEvent.change(prUrlInput, {
      target: { value: "https://github.com/woowacourse-teams/2021-drop-the-code/pull/113" },
    });
    fireEvent.change(contentTextarea, { target: { value: "멋진리뷰 부탁드려요~" } });

    expect(submitButton).toBeDisabled();
  });

  it("pr주소가 유효하지 않은 경우 리뷰를 요청할 수 없다.", async () => {
    const titleInput = await findByRole("textbox", { name: "타이틀" });
    const prUrlInput = getByRole("textbox", { name: "리뷰 요청 Pull Request주소" });
    const contentTextarea = getByRole("textbox", { name: "본문" });
    const submitButton = getByRole("button", { name: "요청" });

    fireEvent.change(titleInput, { target: { value: "리뷰요청" } });
    fireEvent.change(prUrlInput, {
      target: { value: "https://github.com/woowacourse-teams/2021-drop-the-code/poll/113" },
    });
    fireEvent.change(contentTextarea, { target: { value: "멋진리뷰 부탁드려요~" } });

    expect(submitButton).toBeDisabled();
  });

  it("본문이 입력되지 않은 경우 리뷰를 요청할 수 없다.", async () => {
    const titleInput = await findByRole("textbox", { name: "타이틀" });
    const prUrlInput = getByRole("textbox", { name: "리뷰 요청 Pull Request주소" });
    const submitButton = getByRole("button", { name: "요청" });

    fireEvent.change(titleInput, { target: { value: "리뷰요청" } });
    fireEvent.change(prUrlInput, {
      target: { value: "https://github.com/woowacourse-teams/2021-drop-the-code/pull/113" },
    });

    expect(submitButton).toBeDisabled();
  });

  it("제목, 본문, pr주소가 유효한 경우 리뷰를 요청할 수 있다.", async () => {
    const titleInput = await findByRole("textbox", { name: "타이틀" });
    const prUrlInput = getByRole("textbox", { name: "리뷰 요청 Pull Request주소" });
    const contentTextarea = getByRole("textbox", { name: "본문" });
    const submitButton = getByRole("button", { name: "요청" });

    fireEvent.change(titleInput, { target: { value: "리뷰요청" } });
    fireEvent.change(prUrlInput, {
      target: { value: "https://github.com/woowacourse-teams/2021-drop-the-code/pull/113" },
    });
    fireEvent.change(contentTextarea, { target: { value: "멋진리뷰 부탁드려요~" } });

    expect(submitButton).toBeEnabled();
  });
});
