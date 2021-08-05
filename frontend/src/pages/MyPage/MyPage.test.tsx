import { MemoryRouter, Route, Switch } from "react-router-dom";

import { studentAuth } from "__mock__/data/auth";
import { reviewers } from "__mock__/data/reviewers";
import { mockingAnonymousAuth, mockingStudentAuth, mockingTeacherAuth } from "__mock__/utils/mockingAuth";
import { mockingToken } from "__mock__/utils/mockingToken";
import { fireEvent, render, screen } from "__mock__/utils/testUtils";
import App from "App";
import Main from "pages/Main/Main";
import MyPage from "pages/MyPage/MyPage";
import { SUCCESS_MESSAGE } from "utils/constants/message";
import { PATH } from "utils/constants/path";

const { findByText, findByRole } = screen;

describe("마이페이지 테스트", () => {
  it("사용자가 로그인 하지 않은 경우 메인페이지로 리다이렉션 된다.", async () => {
    mockingAnonymousAuth();

    render(
      <MemoryRouter initialEntries={["/mypage"]}>
        <App />
      </MemoryRouter>
    );

    const title = await findByText("리뷰어 찾기");
    expect(title).toBeVisible();
  });

  it("사용자가 로그인 되어 있는 경우 자신의 이름과 정보를 확인할 수 있다.", async () => {
    mockingToken();
    mockingStudentAuth();

    render(<MyPage />);

    const name = await findByText(studentAuth.name);
    const githubUrl = await findByText(studentAuth.githubUrl);

    expect(name).toBeVisible();
    expect(githubUrl).toBeVisible();
  });

  it("사용자가 로그인 되어 있는 경우 회원 탈퇴하면 메인 페이지로 이동한다", async () => {
    mockingToken();
    mockingStudentAuth();

    render(
      <MemoryRouter initialEntries={["/mypage"]}>
        <Switch>
          <Route path={PATH.MAIN} exact>
            <Main />
          </Route>
          <Route path={PATH.MY_PAGE} exact>
            <MyPage />
          </Route>
        </Switch>
      </MemoryRouter>
    );

    const leaveButton = await findByRole("button", { name: "회원 탈퇴하기" });
    fireEvent.click(leaveButton);

    const confirmButton = await findByRole("button", { name: "확인" });
    fireEvent.click(confirmButton);

    const toast = await findByText(SUCCESS_MESSAGE.API.AUTH.DELETE);
    expect(toast).toBeInTheDocument();

    const title = await findByText("리뷰어 찾기");
    expect(title).toBeVisible();
  });

  describe("사용자가 리뷰어인 경우", () => {
    it("리뷰어 정보가 화면에 나타난다.", async () => {
      mockingToken();
      mockingTeacherAuth();

      render(<MyPage />);

      const title = await findByText(new RegExp(reviewers[0].title));
      const career = await findByText(new RegExp(String(reviewers[0].career + "년")));

      expect(title).toBeVisible();
      expect(career).toBeVisible();
    });

    it("리뷰어 정보를 삭제 할 수 있다.", async () => {
      mockingToken();
      mockingTeacherAuth();

      render(<MyPage />);

      const deleteButton = await findByRole("button", { name: "삭제" });
      fireEvent.click(deleteButton);

      const confirmButton = await findByRole("button", { name: "확인" });
      fireEvent.click(confirmButton);

      const toast = await findByText(SUCCESS_MESSAGE.API.REVIEWER.DELETE);
      expect(toast).toBeInTheDocument();
    });

    it("리뷰어 정보를 수정 할 수 있다.", async () => {
      mockingToken();
      mockingTeacherAuth();

      render(<MyPage />);

      const editButton = await findByRole("button", { name: "수정" });
      fireEvent.click(editButton);

      const confirmButton = await findByRole("button", { name: "확인" });
      fireEvent.click(confirmButton);

      const toast = await findByText(SUCCESS_MESSAGE.API.REVIEWER.EDIT);
      expect(toast).toBeInTheDocument();
    });
  });
});
