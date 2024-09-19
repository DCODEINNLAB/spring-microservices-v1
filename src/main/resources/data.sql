insert into user_details(id, birth_date, name) values (10001,current_date(), 'ranga');
insert into user_details(id, birth_date, name) values (10002,current_date(), 'ravi');
insert into user_details(id, birth_date, name) values (10003,current_date(), 'satish');

insert into post(id, description, user_id) values(20001, 'I want to learn JAVA', 10001);
insert into post(id, description, user_id) values(20002, 'I want to learn Python', 10001);
insert into post(id, description, user_id) values(20003, 'I want to learn AWS', 10002);
insert into post(id, description, user_id) values(20004, 'I want to learn AZURE', 10003);