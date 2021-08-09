package com.wootech.dropthecode.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TeacherProfile 도메인 테스트")
class TeacherProfileTest {

    @Test
    @DisplayName("프로필 정보 업데이트")
    void update() {
        // given
        TeacherProfile teacherProfile = TeacherProfile.builder()
                                                      .title("tite")
                                                      .content("content")
                                                      .career(10)
                                                      .build();

        // when
        teacherProfile.update("[수정] title", "[수정] content", 20);

        // then
        assertThat(teacherProfile.getTitle()).isEqualTo("[수정] title");
        assertThat(teacherProfile.getContent()).isEqualTo("[수정] content");
        assertThat(teacherProfile.getCareer()).isEqualTo(20);
    }

    @Test
    @DisplayName("리뷰 횟수와 시간 업데이트")
    void updateReviewCountAndTime() {
        // given
        TeacherProfile teacherProfile = TeacherProfile.builder()
                                                      .title("tite")
                                                      .content("content")
                                                      .career(10)
                                                      .averageReviewTime(6.0)
                                                      .sumReviewCount(4)
                                                      .build();

        // when
        teacherProfile.updateReviewCountAndTime(3L);

        // then
        assertThat(teacherProfile.getSumReviewCount()).isEqualTo(5);
        assertThat(teacherProfile.getAverageReviewTime()).isEqualTo(4.8);
    }

    @Test
    @DisplayName("리뷰 횟수와 시간 업데이트 - 평균 시간이 빈 경우")
    void updateReviewCountAndTimeWhenAverageZero() {
        // given
        TeacherProfile teacherProfile = TeacherProfile.builder()
                                                      .title("tite")
                                                      .content("content")
                                                      .career(10)
                                                      .sumReviewCount(0)
                                                      .build();

        // when
        teacherProfile.updateReviewCountAndTime(3L);

        // then
        assertThat(teacherProfile.getSumReviewCount()).isEqualTo(1);
        assertThat(teacherProfile.getAverageReviewTime()).isEqualTo(0.1);
    }

    @Test
    @DisplayName("프로필 삭제")
    void deleteWithMember() {
        // given
        TeacherProfile teacherProfile = TeacherProfile.builder()
                                                      .title("tite")
                                                      .content("content")
                                                      .career(10)
                                                      .build();

        // when
        teacherProfile.deleteWithMember();

        // then
        assertThat(teacherProfile.getTitle()).isEqualTo("탈퇴한 사용자입니다.");
        assertThat(teacherProfile.getContent()).isEqualTo("내용 없음");
        assertThat(teacherProfile.getCareer()).isEqualTo(0);
    }
}
