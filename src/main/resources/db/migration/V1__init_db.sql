create table if not exists users(
    id int auto_increment primary key,
    chat_id int not null,
    username varchar(100) not null,
    full_name varchar(150),
    departure varchar(50),
    password varchar(20)
  --  id_list int not null,
   -- foreign key (id_list) references list_of_employees(id)
);

create table if not exists messages(
	id int auto_increment primary key,
    description text not null
);

create table if not exists list_of_employees(
	id int auto_increment primary key,
    date date not null,
    id_user int not null,
-- at_word boolean default false,
 --   number_of_list bigint not null,
    foreign key (id_user) references users(id)
);