create table if not exists users(
    id bigint auto_increment primary key,
    chat_id int not null,
    full_name varchar(150),
    password varchar(20),
    at_work bit(1) default 0,
    time_of_coming time,
    room int,
    phone_number varchar(15),
    status enum('WORK','SICK','VACATION'),
    date_of_birth date
);

create table if not exists notifications (
    id bigint auto_increment primary key,
    time_of_notification varchar(20),
    turn_on bit(1) default 1,
    id_user bigint,
    foreign key (id_user) references users(id)
);