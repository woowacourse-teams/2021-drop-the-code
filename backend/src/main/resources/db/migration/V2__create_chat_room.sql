create table chat
(
    id          bigint   not null auto_increment,
    created_at  datetime(6) not null,
    updated_at  datetime(6),
    content     longtext not null,
    receiver_id bigint   not null,
    room_id     bigint   not null,
    sender_id   bigint   not null,
    primary key (id)
) engine=InnoDB;


create table feedback
(
    id      bigint   not null,
    comment longtext not null,
    star    integer  not null,
    primary key (id)
) engine = InnoDB;


create table language
(
    id         bigint       not null auto_increment,
    created_at datetime(6) not null,
    updated_at datetime(6),
    name       varchar(255) not null,
    primary key (id)
) engine = InnoDB;


create table language_skill
(
    id          bigint not null auto_increment,
    language_id bigint,
    skill_id    bigint,
    primary key (id)
) engine = InnoDB;


create table member
(
    id         bigint       not null auto_increment,
    created_at datetime(6) not null,
    updated_at datetime(6),
    email      varchar(255) not null,
    github_url varchar(255),
    image_url  varchar(255) not null,
    name       varchar(255) not null,
    oauth_id   varchar(255),
    role       varchar(255) not null,
    primary key (id)
) engine = InnoDB;


create table review
(
    id           bigint       not null auto_increment,
    created_at   datetime(6) not null,
    updated_at   datetime(6),
    content      longtext     not null,
    elapsed_time bigint,
    pr_url       varchar(255) not null,
    progress     varchar(255) not null,
    title        varchar(255) not null,
    student_id   bigint       not null,
    teacher_id   bigint       not null,
    primary key (id)
) engine = InnoDB;


create table room
(
    id         bigint not null auto_increment,
    created_at datetime(6) not null,
    updated_at datetime(6),
    student_id bigint not null,
    teacher_id bigint not null,
    primary key (id)
) engine=InnoDB;


create table skill
(
    id         bigint       not null auto_increment,
    created_at datetime(6) not null,
    updated_at datetime(6),
    name       varchar(255) not null,
    primary key (id)
) engine = InnoDB;


create table teacher_language
(
    id                 bigint not null auto_increment,
    language_id        bigint,
    teacher_profile_id bigint,
    primary key (id)
) engine = InnoDB;


create table teacher_profile
(
    id                  bigint       not null,
    average_review_time double precision,
    career              integer      not null,
    content             longtext     not null,
    created_at          datetime(6) not null,
    sum_review_count    integer default 0,
    title               varchar(255) not null,
    updated_at          datetime(6),
    primary key (id)
) engine = InnoDB;


create table teacher_skill
(
    id                 bigint not null auto_increment,
    skill_id           bigint,
    teacher_profile_id bigint,
    primary key (id)
) engine = InnoDB;


alter table chat
    add constraint fk_room_to_receiver
        foreign key (receiver_id)
            references member (id);


alter table chat
    add constraint fk_chat_to_room
        foreign key (room_id)
            references room (id);


alter table chat
    add constraint fk_chat_to_sender
        foreign key (sender_id)
            references member (id);


alter table feedback
    add constraint fk_feedback_to_review
        foreign key (id)
            references review (id);


alter table language_skill
    add constraint fk_languageSkill_to_language
        foreign key (language_id)
            references language (id);


alter table language_skill
    add constraint fk_languageSkill_to_skill
        foreign key (skill_id)
            references skill (id);


alter table review
    add constraint fk_review_to_student
        foreign key (student_id)
            references member (id);


alter table review
    add constraint fk_review_to_teacher
        foreign key (teacher_id)
            references member (id);


alter table room
    add constraint fk_room_to_student
        foreign key (student_id)
            references member (id);


alter table room
    add constraint fk_room_to_teacher
        foreign key (teacher_id)
            references member (id);


alter table teacher_language
    add constraint fk_teacherLanguage_to_language
        foreign key (language_id)
            references language (id);


alter table teacher_language
    add constraint fk_teacherLanguage_to_teacherProfile
        foreign key (teacher_profile_id)
            references teacher_profile (id);


alter table teacher_profile
    add constraint fk_teacherProfile_to_member
        foreign key (id)
            references member (id);


alter table teacher_skill
    add constraint fk_teacherSkill_to_skill
        foreign key (skill_id)
            references skill (id);


alter table teacher_skill
    add constraint fk_teacherSkill_to_teacherProfile
        foreign key (teacher_profile_id)
            references teacher_profile (id);

