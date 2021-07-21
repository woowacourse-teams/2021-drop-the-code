package com.wootech.dropthecode.domain;

import com.wootech.dropthecode.exception.AuthorizationException;

public class LoginMember {
    private static final LoginMember ANONYMOUS = new LoginMember();

    private Long id;

    public LoginMember() {
    }

    public LoginMember(Long id) {
        this.id = id;
    }

    public static LoginMember anonymous() {
        return ANONYMOUS;
    }

    public void validatesAnonymous() {
        if (ANONYMOUS.equals(this)) {
            throw new AuthorizationException("유효하지 않은 유저입니다.");
        }
    }

    public Long getId() {
        return id;
    }
}
