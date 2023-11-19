DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS pets;
DROP TABLE IF EXISTS rooms;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255)                            NOT NULL,
    email VARCHAR(254)                            NOT NULL,
    role  VARCHAR(16)                             NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

INSERT INTO users (name, email, role) values ('boss', 'boss@mail.ru', 'ROLE_BOSS');

CREATE TABLE IF NOT EXISTS pets
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    type_pet           VARCHAR(250)                            NOT NULL,
    breed_pet          VARCHAR(250)                            NOT NULL,
    sex_pet            VARCHAR(13)                             NOT NULL,
    age_pet            INTEGER                                 NOT NULL,
    weight_pet         INTEGER                                 NOT NULL,
    diet_pet           VARCHAR(21)                             NOT NULL,
    medication_pet     BOOLEAN                                 NOT NULL,
    contact_pet        BOOLEAN                                 NOT NULL,
    photographed_pet   BOOLEAN                                 NOT NULL,
    comments_pet       VARCHAR(1000)                                   ,
    CONSTRAINT pk_pet PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS rooms
(
    id_room          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    number_room      VARCHAR(100)                            NOT NULL,
    area_room        REAL                                            ,
    type_room        VARCHAR(20)                             NOT NULL,
    description_room VARCHAR(150)                                    ,
    available_room   BOOLEAN DEFAULT TRUE                            ,
    CONSTRAINT pk_room PRIMARY KEY (id_room),
    CONSTRAINT UQ_ROOM_NUMBER UNIQUE(number_room),
    CONSTRAINT positive_room_area CHECK (area_room > 0)
);


