//package com.wootech.dropthecode.acceptance;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//@DisplayName("리뷰 관련 인수 테스트")
//public class ReviewAcceptanceTest extends AcceptanceTest {
//
//    @Nested
//    @DisplayName("리뷰 생성")
//    class CreateReview {
//
//        @Test
//        @DisplayName("리뷰 생성 성공")
//        void createReviewSuccess() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("student id가 빈 경우")
//        void emptyStudentId() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("리뷰를 요청한 학생이 존재하지 않는 학생인 경우")
//        void notExistStudentId() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("teacher id가 빈 경우")
//        void emptyTeacherId() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("리뷰를 요청받은 선생님이 존재하지 않는 선생님인 경우")
//        void notExistTeacherId() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("리뷰 요청 제목인 빈 경우")
//        void emptyTitle() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("리뷰 요청 내용이 빈 경우")
//        void emptyContent() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("리뷰 요청 pr링크가 빈 경우")
//        void emptyPrUrl() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("유효하지 않은 access token")
//        void invalidAccessToken() {
//            // given
//
//            // when
//
//            // then
//        }
//    }
//
//    @Nested
//    @DisplayName("내가 받은 리뷰 목록 조회")
//    class FindStudentReview {
//
//        @Test
//        @DisplayName("내가 받은 리뷰 목록 조회 성공")
//        void findStudentReviewSuccess() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("존재하지 않는 학생인 경우")
//        void notExistStudent() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("로그인 한 유저 id와 받은 리뷰 목록 조희 유저 id가 다른 경우")
//        void findOtherStudentReview() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("유효하지 않은 access token")
//        void invalidAccessToken() {
//            // given
//
//            // when
//
//            // then
//        }
//    }
//
//    @Nested
//    @DisplayName("내가 리뷰한 리뷰 목록 조회")
//    class FindTeacherReview {
//
//        @Test
//        @DisplayName("내가 리뷰한 리뷰 목록 조회 성공")
//        void findTeacherReviewSuccess() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("존재하지 않는 선생님인 경우")
//        void notExistTeacher() {
//            // given
//
//            // when
//
//            // then
//        }
//    }
//
//    @Nested
//    @DisplayName("리뷰 목록 조회")
//    class FindReview {
//
//        @Test
//        @DisplayName("리뷰 상세 목록 조회 성공")
//        void findReviewByIdSuccess() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("존재하지 않는 리뷰 상세 목록 조회")
//        void findReviewByNotExistId() {
//            // given
//
//            // when
//
//            // then
//        }
//    }
//
//    @Nested
//    @DisplayName("리뷰 상태 변경")
//    class ChangeReviewProgress {
//
//        @Test
//        @DisplayName("ON_GOING 상태의 리뷰를 TEACHER_COMPLETE 상태로 변경 성공")
//        void onGoingToTeacherComplete() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("TEACHER_COMPLETE 상태의 리뷰를 FINISHED 상태로 변경 성공")
//        void TeacherCompleteToFinished() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("존재하지 않은 리뷰인 경우")
//        void notExistReview() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("선생님이 아닌데 ON_GOING -> TEACHER_COMPLETE 변경 요청을 하는 경우")
//        void noTeacherWhenUpdateToCompleteReview() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("ON_GOING 상태가 아닌데 ON_GOING -> TEACHER_COMPLETE 변경 요청을 하는 경우")
//        void noOnGoingButUpdateToCompleteReview() {
//            // given
//
//            // when
//
//            // then
//        }
//
//
//        @Test
//        @DisplayName("학생이 아닌데 TEACHER_COMPLETE -> FINISHED 변경 요청을 하는 경우")
//        void noStudentWhenUpdateToFinishReview() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("TEACHER_COMPLETE 상태가 아닌데 TEACHER_COMPLETE -> FINISHED 변경 요청을 하는 경우")
//        void noTeacherCompletedButUpdateToFinishReview() {
//            // given
//
//            // when
//
//            // then
//        }
//
//        @Test
//        @DisplayName("유효하지 않은 access token")
//        void invalidAccessToken() {
//            // given
//
//            // when
//
//            // then
//        }
//    }
//}
