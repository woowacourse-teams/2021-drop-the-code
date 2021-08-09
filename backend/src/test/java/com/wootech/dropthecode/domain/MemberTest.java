package com.wootech.dropthecode.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("member 도메인 테스트")
class MemberTest {

    @Test
    @DisplayName("멤버 정보 업데이트 테스트")
    void update() {
        // given
        Member member = dummyMember("air.junseo@gmail.com", "air", "s3://image", Role.STUDENT);

        // when
        Member updatedMember = member.update("max9106@naver.com", "junseo", "s3://image2");

        // then
        assertThat(updatedMember).usingRecursiveComparison()
                                 .isEqualTo(dummyMember("max9106@naver.com", "junseo", "s3://image2", Role.STUDENT));
    }

    @Test
    @DisplayName("삭제되었던 멤버 정보 업데이트 테스트")
    void updateDeletedMember() {
        // given
        Member member = dummyMember("air.junseo@gmail.com", "air", "s3://image", Role.DELETED);

        // when
        Member updatedMember = member.update("max9106@naver.com", "junseo", "s3://image2");

        // then
        assertThat(updatedMember).usingRecursiveComparison()
                                 .isEqualTo(dummyMember("max9106@naver.com", "junseo", "s3://image2", Role.STUDENT));
    }

    @Test
    @DisplayName("Role 비교")
    void hasRole() {
        // given
        Member member = dummyMember("air.junseo@gmail.com", "air", "s3://image", Role.STUDENT);

        // when
        boolean success = member.hasRole(Role.STUDENT);
        boolean fail = member.hasRole(Role.TEACHER);

        // then
        assertThat(success).isTrue();
        assertThat(fail).isFalse();
    }

    @Test
    @DisplayName("Id 비교")
    void hasSameId() {
        // given
        Member member = dummyMember(1L, "oauthId", "air.junseo@gmail.com", "air", "s3://image", "github Url", Role.STUDENT, null);

        // when
        boolean success = member.hasSameId(1L);
        boolean fail = member.hasSameId(2L);

        // then
        assertThat(success).isTrue();
        assertThat(fail).isFalse();
    }

    @Test
    @DisplayName("멤버 삭제 테스트 - 학생인 경우")
    void deleteStudent() {
        // given
        Member member = dummyMember(1L, "oauthId", "air.junseo@gmail.com", "air", "s3://image", "github Url", Role.STUDENT, null);

        // when
        member.delete();

        // then
        assertThat(member.getRole()).isEqualTo(Role.DELETED);
        assertThat(member.getEmail()).isEqualTo("unknown@dropthecode.co.kr");
        assertThat(member.getName()).isEqualTo("탈퇴한 사용자");
        assertThat(member.getImageUrl()).isEqualTo("https://static.thenounproject.com/png/994628-200.png");
    }

    @Test
    @DisplayName("멤버 삭제 테스트 - 선생님인 경우")
    void deleteTeacher() {
        // given
        Member member = dummyMember(1L, "oauthId", "air.junseo@gmail.com", "air",
                "s3://image", "github Url", Role.TEACHER, TeacherProfile.builder().build());

        // when
        member.delete();

        // then
        assertThat(member.getRole()).isEqualTo(Role.DELETED);
        assertThat(member.getEmail()).isEqualTo("unknown@dropthecode.co.kr");
        assertThat(member.getName()).isEqualTo("탈퇴한 사용자");
        assertThat(member.getImageUrl()).isEqualTo("https://static.thenounproject.com/png/994628-200.png");
        assertThat(member.getTeacherProfile().getTitle()).isEqualTo("탈퇴한 사용자입니다.");
        assertThat(member.getTeacherProfile().getContent()).isEqualTo("내용 없음");
        assertThat(member.getTeacherProfile().getCareer()).isEqualTo(0);
    }
}
