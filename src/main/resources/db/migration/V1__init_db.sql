create table if not exists users(
    id bigint auto_increment primary key,
    chat_id int not null,
    full_name varchar(150) not null,
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


create table if not exists statuses(
    id bigint auto_increment primary key,
    status enum('WORK','SICK','VACATION'),
    started_at date,
    ended_at date,
    id_user bigint,
    foreign key (id_user) references users(id)
);

create table if not exists work_days(
	id bigint auto_increment primary key,
    day date
);

create table if not exists list_users_of_the_day(
	id bigint auto_increment primary key,
    id_user bigint,
    id_work_day bigint,
    time_of_coming time,
    foreign key (id_user) references users(id),
    foreign key (id_work_day) references work_days(id)
);

create table if not exists counter_of_users(
	id bigint auto_increment primary key,
    count int,
    id_user bigint,
    foreign key (id_user) references users(id)
);