import { MemoryRouter, Route, Switch } from "react-router-dom";

import { studentAuth } from "__mock__/data/auth";
import { languages } from "__mock__/data/languages";
import { reviewers } from "__mock__/data/reviewers";
import { mockingAnonymousAuth, mockingStudentAuth, mockingTeacherAuth } from "__mock__/utils/mockingAuth";
import { mockingToken } from "__mock__/utils/mockingToken";
import { fireEvent, render, screen } from "__mock__/utils/testUtils";
import { waitForLoadingToBeRemoved } from "__mock__/utils/wait";
import AuthRoute from "components/Auth/AuthRoute/AuthRoute";
import Header from "components/Header/Header";
import Main from "pages/Main/Main";
import MyPage from "pages/MyPage/MyPage";
import NotFound from "pages/NotFound/NotFound";
import { SUCCESS_MESSAGE } from "utils/constants/message";
import { PATH } from "utils/constants/path";
import { ROUTE } from "utils/constants/route";

const { findByText, findByRole } = screen;

describe("마이페이지 테스트", () => {
  describe("로그인 하지 않은경우", () => {
    it("페이지 진입시 메인페이지로 리다이렉션 된다.", async () => {
      mockingAnonymousAuth();

      render(
        <MemoryRouter initialEntries={["/mypage"]}>
          <Switch>
            {ROUTE.map(({ path, Component, isPrivate, exact }) => (
              <AuthRoute isPrivate={isPrivate} key={path} path={path} redirectTo={PATH.MAIN} exact={exact}>
                <Header />
                <main>
                  <Component />
                </main>
              </AuthRoute>
            ))}
            <Route path={PATH.NOT_FOUND}>
              <NotFound />
            </Route>
          </Switch>
        </MemoryRouter>
      );

      const title = await findByText("리뷰어 찾기");
      expect(title).toBeVisible();
    });
  });

  describe("사용자가 로그인 되어 있는 경우", () => {
    beforeEach(() => {
      mockingToken();
      mockingStudentAuth();
    });

    it("자신의 이름, 깃허브 링크가 화면에 나타난다.", async () => {
      render(<MyPage />);

      const name = await findByText(studentAuth.name);
      const githubUrl = await findByText(studentAuth.githubUrl);

      expect(name).toBeVisible();
      expect(githubUrl).toBeVisible();
    });

    it("회원 탈퇴하면 메인 페이지로 이동한다", async () => {
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
  });

  describe("사용자가 리뷰어인 경우", () => {
    beforeEach(async () => {
      mockingToken();
      mockingTeacherAuth();

      render(<MyPage />);
      await waitForLoadingToBeRemoved();
    });

    it("리뷰어 정보가 화면에 나타난다.", async () => {
      const title = await findByText(new RegExp(reviewers[0].title), {});
      const career = await findByText(new RegExp(String(reviewers[0].career + "년")));
      expect(title).toBeVisible();
      expect(career).toBeVisible();
    });

    it("리뷰어 정보를 삭제 할 수 있다.", async () => {
      const deleteButton = await findByRole("button", { name: "삭제" });
      fireEvent.click(deleteButton);

      const confirmButton = await findByRole("button", { name: "확인" });
      fireEvent.click(confirmButton);

      const toast = await findByText(SUCCESS_MESSAGE.API.REVIEWER.DELETE);
      expect(toast).toBeInTheDocument();
    });

    it("리뷰어 정보를 수정 할 수 있다.", async () => {
      const editButton = await findByRole("button", { name: "수정" });
      fireEvent.click(editButton);
      const language = await findByText(languages[0].language.name);
      fireEvent.click(language);

      const confirmButton = await findByRole("button", { name: "확인" });
      fireEvent.click(confirmButton);

      const toast = await findByText(SUCCESS_MESSAGE.API.REVIEWER.EDIT);
      expect(toast).toBeInTheDocument();
    });
  });
});
