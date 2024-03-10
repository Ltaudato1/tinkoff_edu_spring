CREATE TABLE links (
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    url             TEXT NOT NULL,
    last_update_time TIMESTAMP WITH TIME ZONE,

    PRIMARY KEY (id),
    UNIQUE (url)
);
