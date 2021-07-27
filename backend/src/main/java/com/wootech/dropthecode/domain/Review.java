package com.wootech.dropthecode.domain;

import java.sql.Timestamp;
import javax.persistence.*;

import com.wootech.dropthecode.exception.AuthorizationException;
import com.wootech.dropthecode.exception.ReviewException;

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

    private Long elapsedTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Progress progress;

    public Review() {
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

    public Review(Member teacher, Member student, String title, String content, String prUrl) {
        this(teacher, student, title, content, prUrl, 0L, Progress.ON_GOING);
    }

    public void updateElapsedTime() {
        long now = System.currentTimeMillis();
        long createTime = Timestamp.valueOf(getCreatedAt()).getTime();

        elapsedTime = now - createTime;
    }

    public Long calculateElapsedTime() {
        return elapsedTime / (1000 * 60 * 60);
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

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public void setTeacherCompleteProgress() {
        if (this.progress != Progress.ON_GOING) {
            throw new ReviewException("현재 리뷰는 리뷰 진행중 상태가 아닙니다. 리뷰 완료로 진행시킬 수 없습니다.");
        }
        this.progress = Progress.TEACHER_COMPLETED;
    }

    public void setFinishedProgress() {
        if (this.progress != Progress.TEACHER_COMPLETED) {
            throw new ReviewException("현재 리뷰는 리뷰 완료 상태가 아닙니다. 리뷰 종료로 진행시킬 수 없습니다.");
        }
        this.progress = Progress.FINISHED;
    }

    public void validateMemberIdAsTeacher(Long id) {
        if(!teacher.isSameIdAs(id)) {
            throw new AuthorizationException("현재 사용자는 해당 리뷰에 대한 리뷰 완료 권한이 없습니다.");
        }
    }

    public void validateMemberIdAsStudent(Long id) {
        if(!student.isSameIdAs(id)) {
            throw new AuthorizationException("현재 사용자는 해당 리뷰에 대한 리뷰 완료 권한이 없습니다.");
        }
    }
}
