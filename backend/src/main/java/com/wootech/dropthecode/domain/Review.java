package com.wootech.dropthecode.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.ReviewException;

@Entity
public class Review extends BaseEntity {

    public static final int ONE_SECOND_TO_MILLISECONDS = 1000;
    public static final int ONE_MINUTE_TO_SECONDS = 60;
    public static final int ONE_HOUR_TO_MINUTES = 60;

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

    private Long elapsedTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Progress progress;

    protected Review() {
    }

    public Review(Member teacher, Member student, String title, String content, String prUrl) {
        this(teacher, student, title, content, prUrl, 0L, Progress.ON_GOING);
    }

    public Review(Member teacher, Member student, String title, String content, String prUrl, Long elapsedTime) {
        this(teacher, student, title, content, prUrl, elapsedTime, Progress.ON_GOING);
    }

    public Review(Member teacher, Member student, String title, String content, String prUrl, Long elapsedTime, Progress progress) {
        this.teacher = teacher;
        this.student = student;
        this.title = title;
        this.content = content;
        this.prUrl = prUrl;
        this.elapsedTime = elapsedTime;
        this.progress = progress;
    }

    public void completeProgress(Long memberId) {
        validateMemberIdAsTeacher(memberId);
        validateReviewProgressIsOnGoing();

        this.progress = Progress.TEACHER_COMPLETED;

        updateElapsedTime();
    }

    public void finishProgress(Long memberId) {
        validateMemberIdAsStudent(memberId);
        validateReviewProgressIsTeacherCompeted();

        this.progress = Progress.FINISHED;
    }

    public void validateMemberIdAsTeacher(Long id) {
        if (!teacher.hasSameId(id)) {
            throw new AuthorizationException("현재 사용자는 해당 리뷰에 대한 리뷰 완료 권한이 없습니다.");
        }
    }

    public void validateMemberIdAsStudent(Long id) {
        if (!student.hasSameId(id)) {
            throw new AuthorizationException("현재 사용자는 해당 리뷰에 대한 리뷰 종료 권한이 없습니다.");
        }
    }

    private void validateReviewProgressIsOnGoing() {
        if (this.progress != Progress.ON_GOING) {
            throw new ReviewException("현재 리뷰는 리뷰 진행중 상태가 아닙니다. 리뷰 완료로 진행시킬 수 없습니다.");
        }
    }
    private void validateReviewProgressIsTeacherCompeted() {
        if (this.progress != Progress.TEACHER_COMPLETED) {
            throw new ReviewException("현재 리뷰는 리뷰 완료 상태가 아닙니다. 리뷰 종료로 진행시킬 수 없습니다.");
        }
    }

    private void updateElapsedTime() {
        LocalDateTime createdAt = getCreatedAt();
        if(Objects.isNull(createdAt)) {
            elapsedTime = 0L;
            return;
        }

        long now = System.currentTimeMillis();
        long createTime = Timestamp.valueOf(createdAt).getTime();
        elapsedTime = now - createTime;
    }

    public Long calculateElapsedTime() {
        return elapsedTime / (ONE_SECOND_TO_MILLISECONDS * ONE_MINUTE_TO_SECONDS * ONE_HOUR_TO_MINUTES);
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

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public Progress getProgress() {
        return progress;
    }
}
