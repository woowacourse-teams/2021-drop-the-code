package com.wootech.dropthecode.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("member 도메인 테스트")
class MemberTest {

    @Test
    @DisplayName("멤버 정보 업데이트 테스트")
    void update() {
        // given
        Member member = new Member("air.junseo@gmail.com", "air", "s3://image", Role.STUDENT);

        // when
        Member updatedMember = member.update("max9106@naver.com", "junseo", "s3://image2");

        // then
        assertThat(updatedMember).usingRecursiveComparison()
                                 .isEqualTo(new Member("max9106@naver.com", "junseo", "s3://image2", Role.STUDENT));
    }
    
    @Test
    @DisplayName("Role 비교")
    void hasRole() {
        // given
        Member member = new Member("air.junseo@gmail.com", "air", "s3://image", Role.STUDENT);
        
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
        Member member = new Member(1L, "oauthId", "air.junseo@gmail.com", "air", "s3://image", "github Url", Role.STUDENT, null);

        // when
        boolean success = member.hasSameId(1L);
        boolean fail = member.hasSameId(2L);

        // then
        assertThat(success).isTrue();
        assertThat(fail).isFalse();
    }
}
