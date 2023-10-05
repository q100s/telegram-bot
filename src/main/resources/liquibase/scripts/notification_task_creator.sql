-- liquibase formatted sql

-- changeset q100s:1
CREATE TABLE notification_task (
    id bigserial PRIMARY KEY,
    chat_id bigint NOT NULL,
    notification_text TEXT NOT NULL,
    notification_time TIMESTAMP NOT NULL
);