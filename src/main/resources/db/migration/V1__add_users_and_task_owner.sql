
CREATE TABLE users
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    created_at    TIMESTAMP   NOT NULL
);

CREATE TABLE tasks
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    details    VARCHAR(1000)     NOT NULL,
    completed  BOOLEAN   NOT NULL DEFAULT false,
    owner_id   BIGINT    NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    due_date   TIMESTAMP,

    CONSTRAINT fk_tasks_owner
        FOREIGN KEY (owner_id) REFERENCES users(id)
);


