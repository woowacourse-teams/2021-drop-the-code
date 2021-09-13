create table notification
(
    id          bigint   not null auto_increment,
    review_id   bigint   not null,
    content     varchar(255) not null,
    url         varchar(255),
    is_read     tinyint(1),
    created_at  datetime(6) not null,
    updated_at  datetime(6),
    primary key (id)
) engine=InnoDB;

alter table notification
    add constraint fk_notification_to_review
        foreign key (review_id)
            references review (id);
