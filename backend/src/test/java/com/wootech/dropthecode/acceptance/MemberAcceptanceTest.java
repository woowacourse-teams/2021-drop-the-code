package com.wootech.dropthecode.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("멤버 관련 인수 테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Nested
    @DisplayName("로그인 한 유저 정보 조회")
    class MembersMe {

        @Test
        @DisplayName("로그인 한 유저 정보 조회 성공")
        void membersMeSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("멤버 삭제")
    class DeleteMember {

        @Test
        @DisplayName("멤버 삭제 성공")
        void deleteMemberSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("존재하지 않는 멤버 아이디로 삭제 요청")
        void notExistMember() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("선생님 등록")
    class RegisterTeacher {

        @Test
        @DisplayName("선생님 등록 성공")
        void registerTeacherSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("자기소개 제목이 빈 경우")
        void emptyTitle() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("자기소개 내용이 빈 경우")
        void emptyContent() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님 기술 스펙이 빈 경우")
        void emptyTechSpec() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("이미 선생님으로 등록된 경우")
        void alreadyRegisterTeacher() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님 기술 스펙에 존재하지 않는 언어가 들어있는 경우")
        void notExistLanguageInTeacherTechSpec() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님 기술 스펙에 존재하지 않는 기술이 들어있는 경우")
        void notExistSkillInTeacherTechSpec() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님 기술 스펙에 언어에 포함되지 않는 기술이 있는 경우")
        void notContainSkill() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("유효하지 않은 access token")
        void invalidAccessToken() {
            // given

            // when

            // then
        }
    }

    @Nested
    @DisplayName("선생님 목록 조회")
    class findTeacher {

        @Test
        @DisplayName("선생님 전체 목록 조회 성공")
        void findAllTeacher() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("언어 필터 자체가 없는 경우")
        void notExistLanguageFilter() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("존재하지 않는 언어로 필터를 거는 경우")
        void notExistLanguage() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님 기술 스펙에 존재하지 않는 언어가 들어있는 경우")
        void notExistLanguageInTeacherTechSpec() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님 기술 스펙에 존재하지 않는 기술이 들어있는 경우")
        void notExistSkillInTeacherTechSpec() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님 단일 조회 성공")
        void findTeacherByIdSuccess() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("선생님 단일 조회 존재하지 않는 멤버 id인 경우")
        void findTeacherByNotExistId() {
            // given

            // when

            // then
        }
    }
}
