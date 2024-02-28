create table if not exists users(
    id int auto_increment primary key,
    chat_id int not null,
    full_name varchar(150),
    password varchar(20),
    at_word boolean default false
);