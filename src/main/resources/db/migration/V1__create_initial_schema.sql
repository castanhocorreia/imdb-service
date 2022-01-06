CREATE TABLE users
(
    user_id            UUID                        NOT NULL,
    full_name          VARCHAR(255)                NOT NULL,
    username           VARCHAR(255)                NOT NULL,
    password           VARCHAR(255)                NOT NULL,
    status             VARCHAR(16)                 NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE roles
(
    role_id UUID        NOT NULL,
    name    VARCHAR(16) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (role_id)
);

ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE users_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_on_users_roles FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_roles_on_users_roles FOREIGN KEY (role_id) REFERENCES roles (role_id);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE genres
(
    genre_id UUID         NOT NULL,
    genre    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_genres PRIMARY KEY (genre_id)
);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE people
(
    person_id  UUID         NOT NULL,
    full_name  VARCHAR(255) NOT NULL,
    bio        VARCHAR(255),
    birth_date date,
    death_date date,
    CONSTRAINT pk_people PRIMARY KEY (person_id)
);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE movies
(
    movie_id           UUID                        NOT NULL,
    title              VARCHAR(255)                NOT NULL,
    description        VARCHAR(255),
    release            SMALLINT,
    average_rating     DOUBLE PRECISION,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_movies PRIMARY KEY (movie_id)
);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE ratings
(
    rating_id UUID    NOT NULL,
    movie_id  UUID    NOT NULL,
    user_id   UUID    NOT NULL,
    stars     INTEGER NOT NULL,
    CONSTRAINT pk_ratings PRIMARY KEY (rating_id)
);

ALTER TABLE ratings
    ADD CONSTRAINT fk_ratings_on_movie FOREIGN KEY (movie_id) REFERENCES movies (movie_id);

ALTER TABLE ratings
    ADD CONSTRAINT fk_ratings_on_user FOREIGN KEY (user_id) REFERENCES users (user_id);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE movies_genres
(
    movie_id UUID NOT NULL,
    genre_id UUID NOT NULL,
    CONSTRAINT pk_movies_genres PRIMARY KEY (movie_id, genre_id)
);

ALTER TABLE movies_genres
    ADD CONSTRAINT fk_movies_on_movies_genres FOREIGN KEY (movie_id) REFERENCES movies (movie_id);

ALTER TABLE movies_genres
    ADD CONSTRAINT fk_genres_on_movies_genres FOREIGN KEY (genre_id) REFERENCES genres (genre_id);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE movies_directors
(
    movie_id    UUID NOT NULL,
    director_id UUID NOT NULL,
    CONSTRAINT pk_movies_directors PRIMARY KEY (movie_id, director_id)
);

ALTER TABLE movies_directors
    ADD CONSTRAINT fk_movies_on_movies_directors FOREIGN KEY (movie_id) REFERENCES movies (movie_id);

ALTER TABLE movies_directors
    ADD CONSTRAINT fk_directors_on_movies_directors FOREIGN KEY (director_id) REFERENCES people (person_id);

/* ------------------------------------------------------------------------------------------------------ */

CREATE TABLE movies_actors
(
    movie_id UUID NOT NULL,
    actor_id UUID NOT NULL,
    CONSTRAINT pk_movies_actors PRIMARY KEY (movie_id, actor_id)
);

ALTER TABLE movies_actors
    ADD CONSTRAINT fk_movies_on_movies_actors FOREIGN KEY (movie_id) REFERENCES movies (movie_id);

ALTER TABLE movies_actors
    ADD CONSTRAINT fk_actors_on_movies_actors FOREIGN KEY (actor_id) REFERENCES people (person_id);
