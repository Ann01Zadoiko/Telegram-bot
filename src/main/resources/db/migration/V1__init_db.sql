create table if not exists users(
    id bigint auto_increment primary key,
    chat_id bigint not null,
    full_name varchar(150),
    at_work bit(1) default 0,
    time_of_coming time,
    phone_number varchar(15),
    status enum('WORK','SICK','VACATION','NOTHING', 'BUSINESS_TRIP', 'REMOTE')
);

create table if not exists notifications (
    id bigint auto_increment primary key,
    time_of_notification varchar(20),
    turn_on bit(1) default 1,
    id_user bigint,
    foreign key (id_user) references users(id)
);