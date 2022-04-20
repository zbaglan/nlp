CREATE TABLE affixes (
    id               BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    ending           VARCHAR,
    translate_ending VARCHAR
);

CREATE TABLE stem_words (
    id   BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    word VARCHAR
);

CREATE TABLE stop_words (
    id   BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    word VARCHAR
);

CREATE TABLE analysed_text (
    id   BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    text VARCHAR
);

CREATE TABLE stemming_text (
                               id   BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
                               text VARCHAR
);