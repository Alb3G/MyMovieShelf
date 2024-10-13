create database reto;

use reto;

CREATE TABLE User(
    id int primary key auto_increment,
    user_name varchar(100) unique,
    password text
);

CREATE TABLE Movie(
    id int primary key auto_increment,
    title tinytext,
    genre varchar(50),
    release_year int,
    description text,
    director varchar(100)
);

CREATE TABLE MovieCopy(
    copy_id serial,
    movie_id int,
    user_id int,
    movie_condition enum('Excellent', 'Good', 'Bad') not null,
    platform enum('DVD', 'BluRay', 'VHS') default 'DVD',
    unique (movie_id, user_id, platform)
);

alter table MovieCopy
    add constraint fk_movie foreign key (movie_id) references Movie(id),
    add constraint fk_user foreign key (user_id) references User(id);

-- Insertar los 3 usuarios
INSERT INTO User (user_name, password) VALUES
('user1', '$2a$12$DY2B1Jo8SjU0Fnxqo7ZlHO.5CmIzbppmTEQHULGwFjk224kckTMJ6'), -- root1
('user2', '$2a$12$Wg9eME0GtCliFnndYos0FuL16fSXhDp1ZHn7KrpfLT/dP4XAQ1IYK'), -- root2
('user3', '$2a$12$KWAnzW2BDPAfP9braSxZD.mAYTzMbgHAAPewcjLGgjBg2YZ.VPhBG'); -- root3

-- Insertar 25 películas
INSERT INTO Movie (title, genre, release_year, description, director) VALUES
('The Shawshank Redemption', 'Drama', 1994, 'Two imprisoned men bond over a number of years.', 'Frank Darabont'),
('The Godfather', 'Crime', 1972, 'The aging patriarch of an organized crime dynasty.', 'Francis Ford Coppola'),
('The Dark Knight', 'Action', 2008, 'When the menace known as the Joker emerges.', 'Christopher Nolan'),
('Pulp Fiction', 'Crime', 1994, 'The lives of two mob hitmen, a boxer, a gangster\'s wife.', 'Quentin Tarantino'),
('The Lord of the Rings: The Fellowship of the Ring', 'Fantasy', 2001, 'A meek Hobbit sets out on a journey.', 'Peter Jackson'),
('Forrest Gump', 'Drama', 1994, 'The presidencies of Kennedy and Johnson, Vietnam, etc.', 'Robert Zemeckis'),
('Inception', 'Sci-Fi', 2010, 'A thief who enters the dreams of others.', 'Christopher Nolan'),
('Fight Club', 'Drama', 1999, 'An insomniac office worker forms an underground fight club.', 'David Fincher'),
('The Matrix', 'Sci-Fi', 1999, 'A computer hacker learns about the true nature of his reality.', 'The Wachowskis'),
('Goodfellas', 'Crime', 1990, 'The story of Henry Hill and his life in the mob.', 'Martin Scorsese'),
('Se7en', 'Thriller', 1995, 'Two detectives hunt a serial killer.', 'David Fincher'),
('Gladiator', 'Action', 2000, 'A former Roman general seeks revenge.', 'Ridley Scott'),
('Saving Private Ryan', 'War', 1998, 'Following the Normandy landings, a group of soldiers.', 'Steven Spielberg'),
('The Silence of the Lambs', 'Thriller', 1991, 'A young FBI agent seeks the help of an imprisoned Dr. Hannibal Lecter.', 'Jonathan Demme'),
('The Green Mile', 'Drama', 1999, 'The lives of guards on Death Row are affected by one of their charges.', 'Frank Darabont'),
('Schindler\'s List', 'Biography', 1993, 'In German-occupied Poland during World War II, Oskar Schindler.', 'Steven Spielberg'),
('Interstellar', 'Sci-Fi', 2014, 'A team of explorers travel through a wormhole in space.', 'Christopher Nolan'),
('Braveheart', 'Drama', 1995, 'Scottish warrior William Wallace leads his countrymen.', 'Mel Gibson'),
('The Departed', 'Crime', 2006, 'An undercover cop and a mole in the police try to identify each other.', 'Martin Scorsese'),
('Whiplash', 'Drama', 2014, 'A promising young drummer enrolls at a cut-throat music conservatory.', 'Damien Chazelle'),
('The Prestige', 'Drama', 2006, 'Two stage magicians engage in a competitive rivalry.', 'Christopher Nolan'),
('The Social Network', 'Biography', 2010, 'The story of the founders of Facebook.', 'David Fincher'),
('Django Unchained', 'Western', 2012, 'With the help of a German bounty hunter, a freed slave.', 'Quentin Tarantino'),
('The Lion King', 'Animation', 1994, 'Lion cub Simba is forced to flee into the wilderness.', 'Roger Allers, Rob Minkoff'),
('The Usual Suspects', 'Mystery', 1995, 'A sole survivor tells of the twists in an intricate plot.', 'Bryan Singer');

-- Insertar copias de películas para los usuarios
INSERT INTO MovieCopy (movie_id, user_id, movie_condition, platform) VALUES
-- Usuario 1
(1, 1, 'Excellent', 'DVD'),
(2, 1, 'Good', 'BluRay'),
(3, 1, 'Bad', 'VHS'),
(4, 1, 'Good', 'DVD'),
(5, 1, 'Excellent', 'BluRay'),
(1, 1, 'Good', 'BluRay'),  -- Película repetida con diferente condición
-- Usuario 2
(6, 2, 'Good', 'DVD'),
(7, 2, 'Excellent', 'BluRay'),
(8, 2, 'Bad', 'VHS'),
(9, 2, 'Good', 'DVD'),
(10, 2, 'Excellent', 'BluRay'),
(6, 2, 'Bad', 'VHS'),  -- Película repetida con diferente condición
-- Usuario 3
(11, 3, 'Excellent', 'DVD'),
(12, 3, 'Good', 'BluRay'),
(13, 3, 'Bad', 'VHS'),
(14, 3, 'Good', 'DVD'),
(15, 3, 'Excellent', 'BluRay'),
(11, 3, 'Good', 'BluRay'),  -- Película repetida con diferente condición

-- Copias adicionales para completar las 25 películas
(16, 1, 'Good', 'DVD'),
(17, 2, 'Bad', 'VHS'),
(18, 3, 'Excellent', 'BluRay'),
(19, 1, 'Good', 'DVD'),
(20, 2, 'Excellent', 'BluRay'),
(21, 3, 'Good', 'VHS'),
(22, 1, 'Bad', 'DVD'),
(23, 2, 'Excellent', 'BluRay'),
(24, 3, 'Good', 'VHS'),
(25, 1, 'Bad', 'DVD');

-- drop database reto;
-- select * from Movie inner join MovieCopy on id = movie_id where user_id = 1;