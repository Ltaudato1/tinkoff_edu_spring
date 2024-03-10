CREATE TABLE chats (
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id         BIGINT NOT NULL,
    username        TEXT,

    PRIMARY KEY (id),
    UNIQUE (chat_id)
);
