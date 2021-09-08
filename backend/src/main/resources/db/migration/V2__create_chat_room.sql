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


create table room
(
    id         bigint not null auto_increment,
    created_at datetime(6) not null,
    updated_at datetime(6),
    student_id bigint not null,
    teacher_id bigint not null,
    primary key (id)
) engine=InnoDB;


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


alter table room
    add constraint fk_room_to_student
        foreign key (student_id)
            references member (id);


alter table room
    add constraint fk_room_to_teacher
        foreign key (teacher_id)
            references member (id);
