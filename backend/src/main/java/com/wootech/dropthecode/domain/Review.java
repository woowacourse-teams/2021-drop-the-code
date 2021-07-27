package com.wootech.dropthecode.domain;

import javax.persistence.*;

@Entity
public class Review extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_review_to_teacher"))
    private Member teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_review_to_student"))
    private Member student;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String prUrl;

    // 선생이 리뷰 완료 버튼을 누르기 까지 경과한 시간(일)
    private Integer elapsedTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Progress progress;

    protected Review() {
    }

    public Review(Member teacher, Member student, String title, String content, String prUrl) {
        this(teacher, student, title, content, prUrl, 0, Progress.ON_GOING);
    }

    public Review(Member teacher, Member student, String title, String content, String prUrl, Integer elapsedTime) {
        this(teacher, student, title, content, prUrl, elapsedTime, Progress.ON_GOING);
    }

    public Review(Member teacher, Member student, String title, String content, String prUrl, Integer elapsedTime, Progress progress) {
        this.teacher = teacher;
        this.student = student;
        this.title = title;
        this.content = content;
        this.prUrl = prUrl;
        this.elapsedTime = elapsedTime;
        this.progress = progress;
    }

    public Member getTeacher() {
        return teacher;
    }

    public Member getStudent() {
        return student;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPrUrl() {
        return prUrl;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public Progress getProgress() {
        return progress;
    }
}
