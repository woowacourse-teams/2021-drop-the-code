package com.wootech.dropthecode.domain;

import java.time.LocalDateTime;

public interface ReviewSummary {
    Long getId();
    String getTitle();
    String getContent();
    Progress getProgress();
    MemberSummary getTeacher();
    MemberSummary getStudent();
    String getPrUrl();
    LocalDateTime getCreatedAt();
}
