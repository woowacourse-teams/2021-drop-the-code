package com.wootech.dropthecode.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.wootech.dropthecode.config.JpaConfig;
import com.wootech.dropthecode.domain.*;
import com.wootech.dropthecode.dto.ReviewSummary;
import com.wootech.dropthecode.dto.request.ReviewSearchCondition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
@ActiveProfiles("test")
class ReviewRepositoryCustomImplTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ReviewRepository reviewRepository;

    private Member airTe;
    private Member allieTe;
    private Member seedStu;
    private Member fafiStu;

    private Review airSeed1;
    private Review allieSeed1;
    private Review airFafi1;
    private Review allieFafi1;
    private Review airSeed2;
    private Review airFafi2;
    private Review allieSeed2;
    private Review airFafi3;
    private Review airSeed3;
    private Review allieFafi2;
    private Review allieSeed3;

    @BeforeEach
    void setUp() {
        // given
        airTe = new Member("oauth1", "email1@gmail.com", "name1", "s3://imageUrl1", Role.TEACHER);
        allieTe = new Member("oauth2", "email2@gmail.com", "name2", "s3://imageUrl2", Role.TEACHER);
        TeacherProfile teacherProfile1 = new TeacherProfile("title1", "content1", 10, airTe);
        TeacherProfile teacherProfile2 = new TeacherProfile("title2", "content2", 20, allieTe);

        seedStu = new Member("oauth3", "email3@gmail.com", "name3", "s3://imageUrl3", Role.STUDENT);
        fafiStu = new Member("oauth4", "email4@gmail.com", "name4", "s3://imageUrl4", Role.STUDENT);

        em.persist(airTe);
        em.persist(allieTe);
        em.persist(seedStu);
        em.persist(fafiStu);
        em.persist(teacherProfile1);
        em.persist(teacherProfile2);

        airSeed1 = new Review(airTe, seedStu, "review title1", "review content1", "prUrl1", 3);
        allieSeed1 = new Review(allieTe, seedStu, "review title2", "review content2", "prUrl2", 4, Progress.FINISHED);
        airFafi1 = new Review(airTe, fafiStu, "review title3", "review content3", "prUrl3", 4);
        allieFafi1 = new Review(allieTe, fafiStu, "review title4", "review content4", "prUrl4", 7);
        airSeed2 = new Review(airTe, seedStu, "review title5", "review content5", "prUrl5", 8);
        airFafi2 = new Review(airTe, fafiStu, "review title6", "review content6", "prUrl6", 7);
        allieSeed2 = new Review(allieTe, seedStu, "review title7", "review content7", "prUrl7", 2);
        airFafi3 = new Review(airTe, fafiStu, "review title8", "review content8", "prUrl8", 2);
        airSeed3 = new Review(airTe, seedStu, "review title9", "review content9", "prUrl9", 1, Progress.FINISHED);
        allieFafi2 = new Review(allieTe, fafiStu, "review title10", "review content10", "prUrl10", 4);
        allieSeed3 = new Review(allieTe, seedStu, "review title11", "review content11", "prUrl11", 3);

        em.persist(airSeed1);
        em.persist(allieSeed1);
        em.persist(airFafi1);
        em.persist(allieFafi1);
        em.persist(airSeed2);
        em.persist(airFafi2);
        em.persist(allieSeed2);
        em.persist(airFafi3);
        em.persist(airSeed3);
        em.persist(allieFafi2);
        em.persist(allieSeed3);
    }

    @Nested
    @DisplayName("내가 받은 리뷰 목록 조회")
    class ReviewsAsStudent {

        @Test
        @DisplayName("생성일 오름차순 - /reviews/student/{id}?page=0&size=3&sort=createdAt,asc")
        void createdAtAsc() {
            // given
            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(seedStu.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(2);
            assertThat(results.getContent()).extracting("title")
                                            .contains(airSeed1.getTitle(), allieSeed1.getTitle(), airSeed2.getTitle());
        }

        @Test
        @DisplayName("생성일 내림차순 - /reviews/student/{id}?page=0&size=3&sort=createdAt,desc")
        void createdAtDesc() {
            // given
            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(seedStu.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(2);
            assertThat(results.getContent()).extracting("title")
                                            .contains(allieSeed2.getTitle(), airSeed3.getTitle(), allieSeed3.getTitle());
        }

        @Test
        @DisplayName("최대 페이지보다 큰 페이지 - /reviews/student/{id}?page=2&size=3&sort=createdAt,asc")
        void emptyPage() {
            // given
            Pageable pageable = PageRequest.of(2, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(seedStu.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(2);
            assertThat(results.getContent()).isEmpty();
        }

        @Test
        @DisplayName("선생님 이름으로 필터 - /reviews/student/{id}?page=0&size=3&name={teacherName}&sort=createdAt,asc")
        void filterTeacherName() {
            // given
            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), airTe.getName());

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(seedStu.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(1);
            assertThat(results.getContent()).extracting("title")
                                            .contains(airSeed1.getTitle(), airSeed2.getTitle(), airSeed3.getTitle());
        }

        @Test
        @DisplayName("리뷰 상태로 필터 - /reviews/student/{id}?page=0&size=3&progress=FINISHED&sort=createdAt,asc")
        void filterProgress() {
            // given
            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(Collections.singletonList(Progress.FINISHED), null);

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(seedStu.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(1);
            assertThat(results.getContent()).extracting("title")
                                            .contains(allieSeed1.getTitle(), airSeed3.getTitle());
        }

        @Test
        @DisplayName("선생님 이름 & 리뷰 상태로 필터 - /reviews/student/{id}?page=0&size=3&name={teacherName}&progress=ON_GOING,FINISHED&sort=createdAt,asc")
        void filterTeacherNameAndProgress() {
            // given
            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(Arrays.asList(Progress.ON_GOING, Progress.FINISHED), airTe
                    .getName());

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByStudentId(seedStu.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(1);
            assertThat(results.getContent()).extracting("title")
                                            .contains(airSeed1.getTitle(), airSeed2.getTitle(), airSeed3.getTitle());
        }
    }

    @Nested
    @DisplayName("내가 한 리뷰 목록 조회")
    class ReviewsAsTeacher {

        @Test
        @DisplayName("생성일 오름차순 - /reviews/teacher/{id}?page=0&size=2&sort=createdAt,asc")
        void createdAtAsc() {
            // given
            Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByTeacherId(airTe.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(3);
            assertThat(results.getContent()).extracting("title")
                                            .contains(airSeed1.getTitle(), airFafi1.getTitle());
        }

        @Test
        @DisplayName("생성일 내림차순 - /reviews/teacher/{id}?page=0&size=2&sort=createdAt,desc")
        void createdAtDesc() {
            // given
            Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByTeacherId(airTe.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(3);
            assertThat(results.getContent()).extracting("title")
                                            .contains(airFafi3.getTitle(), airSeed3.getTitle());
        }

        @Test
        @DisplayName("최대 페이지보다 큰 페이지 - /reviews/teacher/{id}?page=2&size=3&sort=createdAt,asc")
        void emptyPage() {
            // given
            Pageable pageable = PageRequest.of(2, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), null);

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByTeacherId(airTe.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(2);
            assertThat(results.getContent()).isEmpty();
        }

        @Test
        @DisplayName("학생 이름으로 필터 - /reviews/teacher/{id}?page=0&size=3&name={studentName}&sort=createdAt,asc")
        void filterStudentName() {
            // given
            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(new ArrayList<>(), fafiStu.getName());

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByTeacherId(airTe.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(1);
            assertThat(results.getContent()).extracting("title")
                                            .contains(airFafi1.getTitle(), airFafi2.getTitle(), airFafi3.getTitle());
        }

        @Test
        @DisplayName("리뷰 상태로 필터 - /reviews/teacher/{id}?page=0&size=3&progress=ON_GOING,FINISHED&sort=createdAt,asc")
        void filterProgress() {
            // given
            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(Arrays.asList(Progress.ON_GOING, Progress.FINISHED), null);

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByTeacherId(airTe.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(2);
            assertThat(results.getContent()).extracting("title")
                                            .contains(airSeed1.getTitle(), airFafi1.getTitle(), airSeed2.getTitle());
        }

        @Test
        @DisplayName("학생 이름 & 리뷰 상태로 필터 - /reviews/teacher/{id}?page=0&size=3&name={studentName}&progress=ON_GOING,FINISHED&sort=createdAt,asc")
        void filterStudentNameAndProgress() {
            // given
            Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "createdAt"));
            ReviewSearchCondition condition = new ReviewSearchCondition(Arrays.asList(Progress.ON_GOING, Progress.FINISHED), seedStu
                    .getName());

            // when
            Page<ReviewSummary> results = reviewRepository.searchPageByTeacherId(airTe.getId(), condition, pageable);

            // then
            assertThat(results.getTotalPages()).isEqualTo(1);
            assertThat(results.getContent()).extracting("title")
                                            .contains(airSeed1.getTitle(), airSeed2.getTitle(), airSeed3.getTitle());
        }
    }
}
