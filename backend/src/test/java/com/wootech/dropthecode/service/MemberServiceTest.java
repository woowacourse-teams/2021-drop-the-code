package com.wootech.dropthecode.service;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import com.wootech.dropthecode.domain.LoginMember;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Role;
import com.wootech.dropthecode.dto.response.MemberResponse;
import com.wootech.dropthecode.exception.AuthenticationException;
import com.wootech.dropthecode.repository.MemberRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wootech.dropthecode.builder.MemberBuilder.dummyMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@DisplayName("MemberService")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("잘못된 로그인 한 유저 아이디를 통해 유저 정보 얻어오기")
    void findByAnonymousLoginMember() {
        // given
        LoginMember loginMember = LoginMember.anonymous();

        // when
        // then
        assertThatThrownBy(() -> memberService.findByLoginMember(loginMember))
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    @DisplayName("로그인 한 유저 아이디를 통해 유저 정보 얻어오기")
    void findByLoginMember() {
        // given
        LoginMember loginMember = new LoginMember(1L);
        given(memberRepository.findById(1L))
                .willReturn(Optional.of(dummyMember(1L, "oauthId", "air.junseo@gmail.com", "air", "s3://image", "githubUrl", Role.STUDENT, null)));

        // when
        MemberResponse result = memberService.findByLoginMember(loginMember);

        // then
        assertThat(result).usingRecursiveComparison()
                          .isEqualTo(new MemberResponse(1L, "air", "air.junseo@gmail.com", "s3://image", "githubUrl", Role.STUDENT));
    }

    @Test
    @DisplayName("유저 아이디로 존재하는 유저 찾기")
    void findById() {
        // given
        Long id = 1L;
        given(memberRepository.findById(id))
                .willReturn(Optional.of(dummyMember(1L, "oauthId", "air", "air.junseo@gmail.com", "s3://image", "github Url", Role.STUDENT, null)));

        // when
        Member member = memberService.findById(id);

        // then
        assertThat(member).usingRecursiveComparison()
                          .isEqualTo(dummyMember(1L, "oauthId", "air", "air.junseo@gmail.com", "s3://image", "github Url", Role.STUDENT, null));
    }

    @Test
    @DisplayName("유저 아이디로 존재하는 않는 유저 찾기")
    void findByIdNonExist() {
        // given
        Long id = 1L;
        given(memberRepository.findById(id))
                .willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> memberService.findById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
