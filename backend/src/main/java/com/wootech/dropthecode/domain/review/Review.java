package com.wootech.dropthecode.domain.review;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

import com.wootech.dropthecode.domain.BaseEntity;
import com.wootech.dropthecode.domain.Feedback;
import com.wootech.dropthecode.domain.Member;
import com.wootech.dropthecode.domain.Progress;
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
        this.teacher = teacher;
        this.student = student;
        this.title = title;
        this.content = content;
        this.prUrl = prUrl;
        this.elapsedTime = elapsedTime;
        this.progress = progress;
    }

    /*
    COMMENT
    기존 메소드는 리뷰의 소유자가 학생인 것 같은 메소드 네이밍이다.
    이 리뷰의 소유는 학생 및 선생이므로, 소유보다는 권한의 의미를 갖도록 메소드 네임을 바꿔보았다.

    기존 Review#validateMemberIdAsStudent() 는 현재 validateAuthorityOfStudent 와 하는 일이 같으므로 삭제
     */
    public void validateAuthorityOfStudent(Long id) {
        if (!this.student.hasSameId(id)) {
            throw new AuthorizationException("리뷰를 수정할 권한이 없습니다!");
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

    /*
    COMMENT
    기존 에러 메시지는 리뷰 진행 중에서 리뷰 완료로 넘어갈 때만 쓰인다. 즉, 범용적이지 않다고 생각된다.
    그래서 다른 곳에서도 재사용될 수 있도록 에러 메시지를 수정했다.
     */
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
