CREATE TABLE IF NOT EXISTS friends (
target_user UUID NOT NULL,
friend UUID NOT NULL,
FOREIGN KEY (target_user) REFERENCES users(id),
FOREIGN KEY (friend) REFERENCES users(id),
UNIQUE (target_user, friend)
);