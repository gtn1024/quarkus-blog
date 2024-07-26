CREATE TABLE t_blog
(
    id         UUID                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    title      VARCHAR(255)                NOT NULL,
    content    TEXT                        NOT NULL,
    CONSTRAINT pk_t_blog PRIMARY KEY (id)
);