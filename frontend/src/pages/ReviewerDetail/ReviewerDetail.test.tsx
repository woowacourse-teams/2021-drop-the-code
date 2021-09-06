import { MemoryRouter, Route } from "react-router-dom";

import { reviewers } from "__mock__/data/reviewers";
import { reviews } from "__mock__/data/reviews";
import { mockingStudentAuth, mockingTeacherAuth } from "__mock__/utils/mockingAuth";
import { mockingToken } from "__mock__/utils/mockingToken";
import { render, screen, fireEvent, waitFor } from "__mock__/utils/testUtils";
import ReviewerDetail from "pages/ReviewerDetail/ReviewerDetail";

const { findAllByText, findByText } = screen;

const mockReviewerDetail = () => {
  render(
    <MemoryRouter initialEntries={["/reviewer/1"]}>
      <Route path="/reviewer/:reviewerId">
        <ReviewerDetail />
      </Route>
    </MemoryRouter>
  );
};

describe("리뷰어 상세페이지 테스트", () => {
  it("리뷰어 상세정보 조회가 끝나면 상세정보를 확인할 수 있다.", async () => {
    mockReviewerDetail();

    const reviewerTitle = await findByText(reviewers[0].title);

    expect(reviewerTitle).toBeInTheDocument();
  });

  it("리뷰 목록 확인하기 버튼을 클릭하면 리뷰 목록을 확인할 수 있다.", async () => {
    mockReviewerDetail();

    const showReviewButton = await findByText("리뷰 목록 확인하기");
    fireEvent.click(showReviewButton);

    const reviewContents = await findAllByText(reviews[0].content);

    expect(reviewContents[0]).toBeInTheDocument();
  });

  it("리뷰어가 내가 아닌경우 리뷰 요청하기 버튼을 클릭할 수 있다.", async () => {
    mockingToken();
    mockingStudentAuth();
    mockReviewerDetail();

    const reviewRequestButton = await findByText("리뷰 요청하기");

    await waitFor(() => expect(reviewRequestButton).toBeEnabled());
  });

  it("리뷰어가 나인경우 리뷰 요청하기 버튼이 리뷰 요청 버튼을 클릭할 수 없다.", async () => {
    mockingToken();
    mockingTeacherAuth();
    mockReviewerDetail();

    const reviewRequestButton = await findByText("리뷰 요청하기");

    await waitFor(() => expect(reviewRequestButton).toBeDisabled());
  });
});
