package com.wootech.dropthecode.domain.review;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.ReviewException;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Feedback feedback;

    @Builder
    public Review(Member teacher, Member student, String title, String content, String prUrl, Long elapsedTime, Progress progress, LocalDateTime createdAt) {
        super(createdAt);
        validateAuthorityOfReviewCreation(teacher, student);
        this.teacher = teacher;
        this.student = student;
        this.title = title;
        this.content = content;
        this.prUrl = prUrl;
        this.elapsedTime = elapsedTime;
        this.progress = progress;
    }

    private void validateAuthorityOfReviewCreation(Member teacher, Member student) {
        if (student.hasSameId(teacher.getId())) {
            throw new ReviewException("자신에게는 리뷰를 요청할 수 없습니다.");
        }

        if (!teacher.hasRole(Role.TEACHER)) {
            throw new ReviewException("리뷰어 권한이 없는 사용자에게는 리뷰를 요청할 수 없습니다.");
        }
    }

    public void validateAuthorityOfStudent(Long id) {
        if (!this.student.hasSameId(id)) {
            throw new AuthorizationException("리뷰 작업에 대한 권한이 없습니다!");
        }
    }

    public void validateAuthorityOfTeacher(Long id) {
        if (!this.teacher.hasSameId(id)) {
            throw new AuthorizationException("리뷰를 수정할 권한이 없습니다!");
        }
    }

    public void validateReviewProgressIsPending() {
        if (!progress.isPending()) {
            throw new ReviewException("현재 리뷰는 리뷰 대기 상태가 아닙니다.");
        }
    }

    public void validateReviewProgressIsOnGoing() {
        if (this.progress != Progress.ON_GOING) {
            throw new ReviewException("현재 리뷰는 리뷰 진행중 상태가 아닙니다.");
        }
    }

    public void validateReviewProgressIsTeacherCompleted() {
        if (this.progress != Progress.TEACHER_COMPLETED) {
            throw new ReviewException("현재 리뷰는 리뷰 완료 상태가 아닙니다");
        }
    }

    public void updateElapsedTime() {
        LocalDateTime createdAt = getCreatedAt();
        if (Objects.isNull(createdAt)) {
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

    public void update(Long id, String title, String content, String prUrl) {
        validateAuthorityOfStudent(id);
        this.title = title;
        this.content = content;
        this.prUrl = prUrl;
    }

    public boolean isPending() {
        return progress.isPending();
    }
}
