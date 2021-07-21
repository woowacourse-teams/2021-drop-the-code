package com.wootech.dropthecode.domain;

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

    public boolean isAnonymous() {
        return ANONYMOUS.equals(this);
    }

    public Long getId() {
        return id;
    }
}
