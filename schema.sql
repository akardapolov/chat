CREATE TABLE users (username VARCHAR(50) NOT NULL, online VARCHAR(20));
ALTER TABLE users ADD CONSTRAINT users_pkey PRIMARY KEY (username);

CREATE TABLE messages (created_time BIGINT NOT NULL, message VARCHAR(200) NOT NULL, from_user VARCHAR(50) NOT NULL, to_user VARCHAR(50) NOT NULL);
ALTER TABLE messages ADD CONSTRAINT messages_pkey PRIMARY KEY (created_time);

ALTER TABLE messages ADD CONSTRAINT fk_f_messages_users FOREIGN KEY (from_user) REFERENCES users (username);
ALTER TABLE messages ADD CONSTRAINT fk_t_messages_users FOREIGN KEY (to_user) REFERENCES users (username);