-- liquibase formatted sql

-- changeset q100s:1
CREATE TABLE notification_task (
    id SERIAL PRIMARY KEY,
    chat_id SERIAL NOT NULL,
    notification_text TEXT NOT NULL,
    notification_time TIMESTAMP NOT NULL
);