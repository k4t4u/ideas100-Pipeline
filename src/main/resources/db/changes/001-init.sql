--liquibase formatted sql

--changeset k4t4u:001_1
DROP table if exists answers CASCADE;
DROP table if exists questions CASCADE;
DROP table if exists categories CASCADE ;

create table categories (
                            id   uuid not null
                                primary key,
                            name varchar(255)
);
create table questions (
                           id          uuid not null
                               primary key,
                           created     timestamp,
                           modified    timestamp,
                           name        varchar(255),
                           category_id uuid
                               references categories
);
create table answers (
                         id          uuid not null
                             primary key,
                         name        varchar(255),
                         question_id uuid
                             references questions
);
--changeset k4t4u:001_2
insert into categories (id, name) values
                                      (gen_random_uuid(), 'Health'),
                                      (gen_random_uuid(), 'Animals'),
                                      (gen_random_uuid(), 'Tourism'),
                                      (gen_random_uuid(), 'Beauty and Style'),
                                      (gen_random_uuid(), 'Culture'),
                                      (gen_random_uuid(), 'Education'),
                                      (gen_random_uuid(), 'Games'),
                                      (gen_random_uuid(), 'Hobby'),
                                      (gen_random_uuid(), 'House and garden'),
                                      (gen_random_uuid(), 'Business'),
                                      (gen_random_uuid(), 'Finances'),
                                      (gen_random_uuid(), 'Culinary'),
                                      (gen_random_uuid(), 'computers'),
                                      (gen_random_uuid(), 'Personal'),
                                      (gen_random_uuid(), 'Automotive'),
                                      (gen_random_uuid(), 'Policy'),
                                      (gen_random_uuid(), 'Work'),
                                      (gen_random_uuid(), 'Presents'),
                                      (gen_random_uuid(), 'Shopping'),
                                      (gen_random_uuid(), 'Electronics'),
                                      (gen_random_uuid(), 'Entertainment'),
                                      (gen_random_uuid(), 'Sex'),
                                      (gen_random_uuid(), 'Relationships'),
                                      (gen_random_uuid(), 'Other');

insert into questions (id, name, category_id) values
    (gen_random_uuid(), 'What are the healthiest vegetables?', (select id from categories where name = 'Health'));

insert into questions (id, name, category_id) values
    (gen_random_uuid(), 'How to best take care of your health?', (select id from categories where name = 'Health'));

insert into questions (id, name, category_id) values
                                                  (gen_random_uuid(), 'Why is it worth learning programming?', (select id from categories where name = 'Education')),
                                                  (gen_random_uuid(), 'Why Java is a good language to start with?', (select id from categories where name = 'Education'));

insert into questions (id, name, category_id) values
                                                  (gen_random_uuid(), 'Where is the best place to spend your holidays in Poland?', (select id from categories where name = 'Tourism')),
                                                  (gen_random_uuid(), 'Where is the best place to spend your holidays from Europe?', (select id from categories where name = 'Tourism'));

insert into answers (id, name, question_id) values
                                                (gen_random_uuid(), 'Mental work', (select id from questions where name = 'Why is it worth learning programming?')),
                                                (gen_random_uuid(), 'High salary', (select id from questions where name = 'Why is it worth learning programming?')),
                                                (gen_random_uuid(), 'continuous development of the mind', (select id from questions where name = 'Why is it worth learning programming?'));

insert into answers (id, name, question_id) values
                                                (gen_random_uuid(), 'Carrot', (select id from questions where name = 'What are the healthiest vegetables?')),
                                                (gen_random_uuid(), 'Broccoli', (select id from questions where name = 'What are the healthiest vegetables?')),
                                                (gen_random_uuid(), 'Pumpkin', (select id from questions where name = 'What are the healthiest vegetables?')),
                                                (gen_random_uuid(), 'Pea', (select id from questions where name = 'What are the healthiest vegetables?'));

insert into answers (id, name, question_id) values
                                                (gen_random_uuid(), 'Gdańsk', (select id from questions where name = 'Where is the best place to spend your holidays in Poland?')),
                                                (gen_random_uuid(), 'Bieszczady', (select id from questions where name = 'Where is the best place to spend your holidays in Poland?')),
                                                (gen_random_uuid(), 'Mazury', (select id from questions where name = 'Where is the best place to spend your holidays in Poland?'));

