package com.wootech.dropthecode.domain;

public enum Progress {
    PENDING("리뷰 수락 대기중"),
    DENIED("선생님이 리뷰 거절"),
    ON_GOING("리뷰 진행중"),
    TEACHER_COMPLETED("선생님의 리뷰가 완료된 상태"),
    FINISHED("리뷰가 완료된 상태");

    private final String description;

    Progress(String description) {
        this.description = description;
    }

    public boolean isPending() {
        return PENDING == this;
    }

    public String getDescription() {
        return description;
    }
}
