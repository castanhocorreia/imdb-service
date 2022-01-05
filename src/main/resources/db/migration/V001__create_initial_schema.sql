CREATE TABLE users
(
    user_id            BINARY(36)   NOT NULL,
    full_name          VARCHAR(255) NOT NULL,
    username           VARCHAR(255) NOT NULL,
    password           VARCHAR(255) NOT NULL,
    status             VARCHAR(16)  NOT NULL,
    created_date       datetime     NOT NULL,
    last_modified_date datetime     NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE roles
(
    role_id BINARY(36)  NOT NULL,
    name    VARCHAR(36) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (role_id)
);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_role UNIQUE (name);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE users_roles
(
    user_id BINARY(36) NOT NULL,
    role_id BINARY(36) NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_on_users_roles FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_roles_on_users_roles FOREIGN KEY (role_id) REFERENCES roles (role_id);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE genres
(
    genre_id BINARY(36)   NOT NULL,
    genre    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_genres PRIMARY KEY (genre_id)
);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE people
(
    person_id          BINARY(36)   NOT NULL,
    full_name          VARCHAR(255) NOT NULL,
    bio                VARCHAR(255) NULL,
    birth_date         date         NULL,
    death_date         date         NULL,
    created_date       datetime     NOT NULL,
    last_modified_date datetime     NOT NULL,
    CONSTRAINT pk_people PRIMARY KEY (person_id)
);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE movies
(
    movie_id           BINARY(36)   NOT NULL,
    title              VARCHAR(255) NOT NULL,
    `description`      VARCHAR(255) NULL,
    `release`          SMALLINT     NULL,
    created_date       datetime     NOT NULL,
    last_modified_date datetime     NOT NULL,
    CONSTRAINT pk_movies PRIMARY KEY (movie_id)
);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE ratings
(
    rating_id BINARY(36) NOT NULL,
    movie_id  BINARY(36) NOT NULL,
    user_id   BINARY(36) NOT NULL,
    stars     DOUBLE     NULL,
    CONSTRAINT pk_ratings PRIMARY KEY (rating_id)
);

ALTER TABLE ratings
    ADD CONSTRAINT fk_ratings_on_movie FOREIGN KEY (movie_id) REFERENCES movies (movie_id);

ALTER TABLE ratings
    ADD CONSTRAINT fk_ratings_on_user FOREIGN KEY (user_id) REFERENCES users (user_id);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE movies_genres
(
    movie_id BINARY(36) NOT NULL,
    genre_id BINARY(36) NOT NULL,
    CONSTRAINT pk_movies_genres PRIMARY KEY (movie_id, genre_id)
);

ALTER TABLE movies_genres
    ADD CONSTRAINT fk_movies_on_movies_genres FOREIGN KEY (movie_id) REFERENCES movies (movie_id);

ALTER TABLE movies_genres
    ADD CONSTRAINT fk_genres_on_movies_genres FOREIGN KEY (genre_id) REFERENCES genres (genre_id);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE movies_directors
(
    movie_id    BINARY(36) NOT NULL,
    director_id BINARY(36) NOT NULL,
    CONSTRAINT pk_movies_directors PRIMARY KEY (movie_id, director_id)
);

ALTER TABLE movies_directors
    ADD CONSTRAINT fk_movies_on_movies_directors FOREIGN KEY (movie_id) REFERENCES movies (movie_id);

ALTER TABLE movies_directors
    ADD CONSTRAINT fk_directors_on_movies_directors FOREIGN KEY (director_id) REFERENCES people (person_id);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE movies_actors
(
    movie_id BINARY(36) NOT NULL,
    actor_id BINARY(36) NOT NULL,
    CONSTRAINT pk_movies_directors PRIMARY KEY (movie_id, actor_id)
);

ALTER TABLE movies_actors
    ADD CONSTRAINT fk_movies_on_movies_actors FOREIGN KEY (movie_id) REFERENCES movies (movie_id);

ALTER TABLE movies_actors
    ADD CONSTRAINT fk_actors_on_movies_actors FOREIGN KEY (actor_id) REFERENCES people (person_id);

/* ------------------------------------------------------------------------------------------------------ */

insert into roles
values ('3f06af63a93c11e4979700505690773f', 'ADMIN');
insert into roles
values ('3f06af63a93c11e4979700505690773a', 'USER');