package com.wootech.dropthecode.domain;

public enum Progress {
    ON_GOING("리뷰 진행중"),
    TEACHER_COMPLETED("선생님의 리뷰가 완료된 상태"),
    FINISHED("리뷰가 완료된 상태");

    private final String description;

    Progress(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
