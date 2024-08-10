CREATE TABLE chats (
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    chat_id         BIGINT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (chat_id)
);
