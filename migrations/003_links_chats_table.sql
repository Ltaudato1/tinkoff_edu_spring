CREATE TABLE links_chats (
    link_id         BIGINT NOT NULL,
    chat_id         BIGINT NOT NULL,

    FOREIGN KEY (link_id) REFERENCES links (id),
    FOREIGN KEY (chat_id) REFERENCES chats (id)
);
