create table if not exists users(
    id int auto_increment primary key,
    chat_id int not null,
    full_name varchar(150) not null,
    password varchar(20),
    at_work bit(1) default 0,
    time_coming time
);