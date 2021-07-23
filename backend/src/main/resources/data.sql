INSERT INTO language (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'java');
INSERT INTO language (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'javascript');
INSERT INTO language (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'python');
INSERT INTO language (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'c++');
INSERT INTO language (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'c');

INSERT INTO skill (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'spring');
INSERT INTO skill (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'vue.js');
INSERT INTO skill (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'react');
INSERT INTO skill (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'unity');
INSERT INTO skill (created_at, updated_at, name)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'django');

INSERT INTO language_skill (language_id, skill_id)
VALUES (1, 1);
INSERT INTO language_skill (language_id, skill_id)
VALUES (2, 2);
INSERT INTO language_skill (language_id, skill_id)
VALUES (2, 3);
INSERT INTO language_skill (language_id, skill_id)
VALUES (3, 5);
INSERT INTO language_skill (language_id, skill_id)
VALUES (4, 4);
INSERT INTO language_skill (language_id, skill_id)
VALUES (5, 4);

INSERT INTO member (created_at, updated_at, email, image_url, name, role)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'seed@gmail.com', 's3://hsik-profile.jpg', '시드', 'TEACHER');

INSERT INTO member (created_at, updated_at, email, image_url, name, role)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'allie@gmail.com', 's3://allie-profile.jpg', '알리', 'TEACHER');

INSERT INTO member (created_at, updated_at, email, image_url, name, role)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'pobi@gmail.com', 's3://pobi-profile.jpg', '포비', 'TEACHER');

INSERT INTO member (created_at, updated_at, email, image_url, name, role)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'air@gmail.com', 's3://air-profile.jpg', '에어', 'TEACHER');

INSERT INTO member (created_at, updated_at, email, image_url, name, role)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'fafi@gmail.com', 's3://fafi-profile.jpg', '파피', 'TEACHER');

INSERT INTO teacher_profile (id, created_at, updated_at, career, content, title, sum_review_count, average_review_time)
VALUES (1, CURRENT_DATE(), CURRENT_DATE(), 5, '배민 1타 강사. 롤체 챌 출신. 열심히 가르쳐드리겠습니다.', '배민 출신 백엔드 개발자', 2, 1.3);
INSERT INTO teacher_profile (id, created_at, updated_at, career, content, title, sum_review_count, average_review_time)
VALUES (2, CURRENT_DATE(), CURRENT_DATE(), 3, '빡세게 가르쳐드리겠습니다.', '쿠팡 이츠 출신 백엔드 개발자', 5, 1.2);
INSERT INTO teacher_profile (id, created_at, updated_at, career, content, title, sum_review_count, average_review_time)
VALUES (3, CURRENT_DATE(), CURRENT_DATE(), 1, '열심히 하겠습니다.', '우테코 갓 졸업생', 10, 0);
INSERT INTO teacher_profile (id, created_at, updated_at, career, content, title, sum_review_count, average_review_time)
VALUES (4, CURRENT_DATE(), CURRENT_DATE(), 6, '화이팅', '배민 인프라 담당자', 1, 10);
INSERT INTO teacher_profile (id, created_at, updated_at, career, content, title, sum_review_count, average_review_time)
VALUES (5, CURRENT_DATE(), CURRENT_DATE(), 10, '실리콘밸리 출신 인프라 개발자. 영어로 수업합니다.', 'Amazon 서버 개발자', 3, 0);

INSERT INTO teacher_language (language_id, teacher_profile_id)
VALUES (1, 1);
INSERT INTO teacher_language (language_id, teacher_profile_id)
VALUES (2, 1);
INSERT INTO teacher_language (language_id, teacher_profile_id)
VALUES (2, 2);
INSERT INTO teacher_language (language_id, teacher_profile_id)
VALUES (3, 3);
INSERT INTO teacher_language (language_id, teacher_profile_id)
VALUES (4, 4);
INSERT INTO teacher_language (language_id, teacher_profile_id)
VALUES (5, 5);

INSERT INTO teacher_skill (skill_id, teacher_profile_id)
VALUES (1, 1);
INSERT INTO teacher_skill (skill_id, teacher_profile_id)
VALUES (2, 1);
INSERT INTO teacher_skill (skill_id, teacher_profile_id)
VALUES (3, 1);
INSERT INTO teacher_skill (skill_id, teacher_profile_id)
VALUES (2, 2);
INSERT INTO teacher_skill (skill_id, teacher_profile_id)
VALUES (3, 2);
INSERT INTO teacher_skill (skill_id, teacher_profile_id)
VALUES (5, 3);
INSERT INTO teacher_skill (skill_id, teacher_profile_id)
VALUES (4, 4);
INSERT INTO teacher_skill (skill_id, teacher_profile_id)
VALUES (4, 5);

INSERT INTO review (created_at, updated_at, content, elapsed_time, pr_url, progress, title, student_id, teacher_id)
VALUES (CURRENT_DATE(), CURRENT_DATE(), 'Spark에서 Spring으로 마이그레이션', 48, 'github.com/pr/1', 'FINISHED', '자동차 경주 리뷰 요청', 1,
        2);

INSERT INTO review (created_at, updated_at, content, elapsed_time, pr_url, progress, title, student_id, teacher_id)
VALUES (CURRENT_DATE(), CURRENT_DATE(), '추상 클래스 사용해봤어요.', 96, 'github.com/pr/1', 'FINISHED', '블랙잭 리뷰 요청', 1, 3);

INSERT INTO review (created_at, updated_at, content, pr_url, progress, title, student_id, teacher_id)
VALUES (CURRENT_DATE(), CURRENT_DATE(), '불변 객체 적용', 'github.com/pr/1', 'ON_GOING', '자동차 경주 리뷰 요청', 1, 4);

INSERT INTO review (created_at, updated_at, content, elapsed_time, pr_url, progress, title, student_id, teacher_id)
VALUES (CURRENT_DATE(), CURRENT_DATE(), '프론트와 협업해봤어요!', 72, 'github.com/pr/1', 'FINISHED', '지하철 리뷰 요청', 2, 3);

INSERT INTO review (created_at, updated_at, content, elapsed_time, pr_url, progress, title, student_id, teacher_id)
VALUES (CURRENT_DATE(), CURRENT_DATE(), '일급 컬력센을 사용해 구현했어요.', 24, 'github.com/pr/1', 'FINISHED', '로또 리뷰 요청', 3, 4);
