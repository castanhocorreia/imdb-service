insert into roles
values ('0403dce0-4ced-4732-91f9-6e0515444755', 'ADMIN');
insert into roles
values ('18033058-4593-4601-aa51-7fb55984d8fc', 'USER');

/* ------------------------------------------------------------------------------------------------------ */

INSERT INTO users(user_id, full_name, username, password, status, created_date, last_modified_date)
VALUES ('e51e3deb-1365-4f44-81c0-4efc787485d6', 'João Correia', 'castanhocorreia',
        '$2a$12$YxXUSMLpAMiMM1aHMlEoVeqGtZxUig11rMJeuGuCWhiELtGRTCLGC', 'ACTIVE', now(), now());

INSERT INTO users_roles(user_id, role_id)
VALUES ('e51e3deb-1365-4f44-81c0-4efc787485d6', '0403dce0-4ced-4732-91f9-6e0515444755');

/* ------------------------------------------------------------------------------------------------------ */

INSERT INTO users(user_id, full_name, username, password, status, created_date, last_modified_date)
VALUES ('d5e92512-826e-4457-9612-f62e67dcdb3e', 'Gilson Vilela Jr', 'gilsonvilelajr',
        '$2a$12$YxXUSMLpAMiMM1aHMlEoVeqGtZxUig11rMJeuGuCWhiELtGRTCLGC', 'ACTIVE', now(), now());

INSERT INTO users_roles(user_id, role_id)
VALUES ('d5e92512-826e-4457-9612-f62e67dcdb3e', '18033058-4593-4601-aa51-7fb55984d8fc');

/* ------------------------------------------------------------------------------------------------------ */

INSERT INTO users(user_id, full_name, username, password, status, created_date, last_modified_date)
VALUES ('1c0d4d57-40e5-4410-801c-f54864c89d4e', 'Walter Neto', 'walterneto',
        '$2a$12$YxXUSMLpAMiMM1aHMlEoVeqGtZxUig11rMJeuGuCWhiELtGRTCLGC', 'ACTIVE', now(), now());

INSERT INTO users_roles(user_id, role_id)
VALUES ('1c0d4d57-40e5-4410-801c-f54864c89d4e', '18033058-4593-4601-aa51-7fb55984d8fc');

/* ------------------------------------------------------------------------------------------------------ */

INSERT INTO genres(genre_id, genre)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11894', 'ACTION');
INSERT INTO genres(genre_id, genre)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11895', 'COMEDY');
INSERT INTO genres(genre_id, genre)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11896', 'CRIME');
INSERT INTO genres(genre_id, genre)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11897', 'DRAMA');
INSERT INTO genres(genre_id, genre)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11898', 'FANTASY');
INSERT INTO genres(genre_id, genre)
VALUES ('b22bd9b6-c005-4d34-b976-83496b3c8706', 'HORROR');
INSERT INTO genres(genre_id, genre)
VALUES ('b22bd9b6-c005-4d34-b976-83496b3c8707', 'MUSICAL');
INSERT INTO genres(genre_id, genre)
VALUES ('b22bd9b6-c005-4d34-b976-83496b3c8708', 'ROMANCE');
INSERT INTO genres(genre_id, genre)
VALUES ('b22bd9b6-c005-4d34-b976-83496b3c8709', 'SATIRE');
INSERT INTO genres(genre_id, genre)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11891', 'SCIFI');
INSERT INTO genres(genre_id, genre)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11892', 'WAR');
INSERT INTO genres(genre_id, genre)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11893', 'WESTERN');

/* ------------------------------------------------------------------------------------------------------ */

INSERT INTO people(person_id, full_name)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11895', 'Peter Jackson');
INSERT INTO people(person_id, full_name)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11894', 'Elijah Wood');
INSERT INTO people(person_id, full_name)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11896', 'Ian McKellen');

INSERT INTO movies(movie_id, title, release, average_rating, created_date, last_modified_date)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11897', 'The Lord of the Rings: The Fellowship of the Ring', '2001', '0.0',
        now(),
        now());

INSERT INTO movies_actors(movie_id, actor_id)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11897', '0c845c4b-f4d0-4dbd-933e-22c6c4d11894');
INSERT INTO movies_actors(movie_id, actor_id)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11897', '0c845c4b-f4d0-4dbd-933e-22c6c4d11896');

INSERT INTO movies_directors(movie_id, director_id)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11897', '0c845c4b-f4d0-4dbd-933e-22c6c4d11895');

INSERT INTO movies_genres(movie_id, genre_id)
VALUES ('0c845c4b-f4d0-4dbd-933e-22c6c4d11897', '0c845c4b-f4d0-4dbd-933e-22c6c4d11898');