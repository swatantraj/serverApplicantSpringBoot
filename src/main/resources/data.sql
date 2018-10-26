/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04');

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');

-- Create 3 Car which are not available
insert into car (id, date_created, license_plate, seat_count, deleted, convertible, available, rating, engine_type, company_name, model) 
values (1, now(), 'AB01-AB1234', 3, false, false, false, 4.7, 'GAS_PETROL', 'ComNam1', '2008LX');

insert into car (id, date_created, license_plate, seat_count, deleted, convertible, available, rating, engine_type, company_name, model) 
values (2, now(), 'AB02-AB2345', 3, false, false, false, 4.9, 'GAS_DIESEL', 'ComNam2', '2009LV');

insert into car (id, date_created, license_plate, seat_count, deleted, convertible, available, rating, engine_type, company_name, model) 
values (3, now(), 'AB03-AB3456', 1, false, true, false, 5.0, 'ELECTRIC', 'ComNam3', '2018CV');

-- Create 3 Cars which are available

insert into car (id, date_created, license_plate, seat_count, deleted, convertible, available, rating, engine_type, company_name, model) 
values (4, now(), 'AB04-AB4567', 6, false, false, true, 4.0, 'GAS_DIESEL', 'ComNam2', '2016SU');

insert into car (id, date_created, license_plate, seat_count, deleted, convertible, available, rating, engine_type, company_name, model) 
values (5, now(), 'AB05-AB5678', 3, false, false, true, 4.2, 'GAS_PETROL', 'ComNam5', '2012LV');

insert into car (id, date_created, license_plate, seat_count, deleted, convertible, available, rating, engine_type, company_name, model) 
values (6, now(), 'AB06-AB6789', 1, false, true, true, 5.0, 'ELECTRIC', 'ComNam2', '2015CV');

-- Create 1 admin role user
insert into appuser (id, date_created, username, password, role) 
values (1, now(), 'admin', 'admin', 'ADMIN');

-- Create 3 user role user
insert into appuser (id, date_created, username, password, role) 
values (2, now(), 'userABCD', 'pass', 'USER');

insert into appuser (id, date_created, username, password, role) 
values (3, now(), 'userEFGH', 'pass', 'USER');

insert into appuser (id, date_created, username, password, role) 
values (4, now(), 'userIJKL', 'pass', 'USER');

insert into driver_x_car (driver_id, car_id) values(4,1);
