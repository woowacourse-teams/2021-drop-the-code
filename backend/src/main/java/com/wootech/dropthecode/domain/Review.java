package com.wootech.dropthecode.domain;

import javax.persistence.*;

@Entity
public class Review extends BaseEntity {
    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_review_to_teacher"))
    private Member teacher;

    @ManyToOne
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
    private Long elapsedTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final Progress progress = Progress.ON_GOING;
}
