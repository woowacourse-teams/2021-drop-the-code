package com.wootech.dropthecode.repository;

import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.EntityManager;

import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReviewRepositoryImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ReviewRepository reviewRepository;

    private Member teacher1;
    private Member teacher2;
    private Member student1;
    private Member student2;

    @BeforeEach
    void setUp() {
        // given
        teacher1 = new Member("oauth1", "email1@gmail.com", "name1", "s3://imageUrl1", Role.TEACHER);
        teacher2 = new Member("oauth2", "email2@gmail.com", "name2", "s3://imageUrl2", Role.TEACHER);
        TeacherProfile teacherProfile1 = new TeacherProfile("title1", "content1", 10, teacher1);
        TeacherProfile teacherProfile2 = new TeacherProfile("title2", "content2", 20, teacher2);

        student1 = new Member("oauth3", "email3@gmail.com", "name3", "s3://imageUrl3", Role.STUDENT);
        student2 = new Member("oauth4", "email4@gmail.com", "name4", "s3://imageUrl4", Role.STUDENT);

        em.persist(teacher1);
        em.persist(teacher2);
        em.persist(student1);
        em.persist(student2);
        em.persist(teacherProfile1);
        em.persist(teacherProfile2);

        Review review1 = new Review(teacher1, student1, "review title1", "review content1", "prUrl1", 3);
        Review review2 = new Review(teacher2, student1, "review title2", "review content2", "prUrl2", 4);
        Review review3 = new Review(teacher1, student2, "review title3", "review content3", "prUrl3", 4);
        Review review4 = new Review(teacher2, student2, "review title4", "review content4", "prUrl4", 7);
        Review review5 = new Review(teacher1, student1, "review title5", "review content5", "prUrl5", 8);
        Review review6 = new Review(teacher1, student2, "review title6", "review content6", "prUrl6", 7);
        Review review7 = new Review(teacher2, student1, "review title7", "review content7", "prUrl7", 2);
        Review review8 = new Review(teacher1, student2, "review title8", "review content8", "prUrl8", 2);
        Review review9 = new Review(teacher1, student1, "review title9", "review content9", "prUrl9", 1);
        Review review10 = new Review(teacher2, student2, "review title10", "review content10", "prUrl10", 4);
        Review review11 = new Review(teacher2, student1, "review title11", "review content11", "prUrl11", 3);

        em.persist(review1);
        em.persist(review2);
        em.persist(review3);
        em.persist(review4);
        em.persist(review5);
        em.persist(review6);
        em.persist(review7);
        em.persist(review8);
        em.persist(review9);
        em.persist(review10);
        em.persist(review11);
    }

    @Test
    @DisplayName("생성일 오름차순 - /reviews/student/{id}?page=0&size=3&sort=createdAt,asc")
    void createdAtAsc() {
        // given
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
        ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

        // when
        Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(student1.getId(), condition, pageable);

        // then
        assertThat(results.getTotalPages()).isEqualTo(2);
        assertThat(results.getContent()).extracting("title")
                                        .contains("review title1", "review title2", "review title5");
    }

    @Test
    @DisplayName("생성일 내림차순 - /reviews/student/{id}?page=0&size=3&sort=createdAt,desc")
    void createdAtDesc() {
        // given
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));
        ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

        // when
        Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(student1.getId(), condition, pageable);

        // then
        assertThat(results.getTotalPages()).isEqualTo(2);
        assertThat(results.getContent()).extracting("title")
                                        .contains("review title7", "review title9", "review title11");
    }

    @Test
    @DisplayName("최대 페이지보다 큰 페이지 - /reviews/student/{id}?page=2&size=3&sort=createdAt,asc")
    void emptyPage() {
        // given
        Pageable pageable = PageRequest.of(2, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
        ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

        // when
        Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(student1.getId(), condition, pageable);

        // then
        assertThat(results.getTotalPages()).isEqualTo(2);
        assertThat(results.getContent()).isEmpty();
    }

    @Test
    @DisplayName("선생님 이름으로 필터 - /reviews/student/{id}?page=0&size=3&name={teacherName}&sort=createdAt,asc")
    void filterTeacherName() {
        // given
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
        ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), teacher1.getName());

        // when
        Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(student1.getId(), condition, pageable);

        // then
        assertThat(results.getTotalPages()).isEqualTo(1);
        assertThat(results.getContent()).extracting("title")
                                        .contains("review title1", "review title5", "review title9");
    }

    @Test
    @DisplayName("리뷰 상태로 필터 - /reviews/student/{id}?page=0&size=3&progress=ON_GOING,FINISHED&sort=createdAt,asc")
    void filterProgress() {
        // given
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
        ReviewSearchCondition condition = new ReviewSearchCondition(Arrays.asList(Progress.ON_GOING, Progress.FINISHED), null);

        // when
        Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(student1.getId(), condition, pageable);

        // then
        assertThat(results.getTotalPages()).isEqualTo(2);
        assertThat(results.getContent()).extracting("title")
                                        .contains("review title1", "review title2", "review title5");
    }

    @Test
    @DisplayName("선생님 이름 & 리뷰 상태로 필터 - /reviews/student/{id}?page=0&size=3&name={teacherName}&progress=ON_GOING,FINISHED&sort=createdAt,asc")
    void filterTeacherNameAndProgress() {
        // given
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
        ReviewSearchCondition condition = new ReviewSearchCondition(Arrays.asList(Progress.ON_GOING, Progress.FINISHED), teacher1
                .getName());

        // when
        Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(student1.getId(), condition, pageable);

        // then
        assertThat(results.getTotalPages()).isEqualTo(1);
        assertThat(results.getContent()).extracting("title")
                                        .contains("review title1", "review title5", "review title9");
    }

}