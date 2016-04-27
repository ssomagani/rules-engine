--
-- Table to store the stream car movement events
--
create table CAR_MOVEMENT (
	car_id integer not null,
	velocity float not null,
	distance integer not null,
	intersection varchar not null
);

--
-- Table to store new cars that come up on the radar
--
create table NEW_CAR (
	car_id integer not null,
	velocity float not null,
	distance integer not null,
	intersection varchar not null
);

--
-- Table to store the predicate strings
--
create table PREDICATE_STRINGS (
	id varchar not null primary key,
	string varchar not null
);

--
-- Populating the PREDICATE_STRINGS table with a couple of predicates 
-- that the TestSP proc will use to demonstrate the usage of this Rules Engine
--
insert into PREDICATE_STRINGS values ('new_car', 'function(x) { if(x == 0) return true; else return false;}');
insert into PREDICATE_STRINGS values ('velocity', 'function(x) { if(x > 100) return false; else return true;}');

create procedure from class TestSP;
