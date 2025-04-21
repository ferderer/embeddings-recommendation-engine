SET time_zone = "+00:00";
SET NAMES 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';

create table lesson (
    id              bigint          primary key,
    title           varchar(80)     not null unique,
    lang            char(2)         not null,
    cefr            char(2)         not null,
    embedding       blob
);

create table lesson_similarities (
    lesson_id1      bigint          not null,
    lesson_id2      bigint          not null,
    similarity      float           not null,
    primary key (lesson_id1, lesson_id2),
    constraint fk_similarities_lesson_id1 foreign key (lesson_id1) references lesson(id),
    constraint fk_similarities_lesson_id2 foreign key (lesson_id2) references lesson(id)
);
