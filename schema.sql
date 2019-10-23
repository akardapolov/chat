CREATE TABLE users (username VARCHAR(50) NOT NULL, online VARCHAR(20));
ALTER TABLE users ADD CONSTRAINT users_pkey PRIMARY KEY (username);

CREATE TABLE messages (created_time BIGINT NOT NULL, message VARCHAR(200) NOT NULL, from_user VARCHAR(50) NOT NULL, to_user VARCHAR(50) NOT NULL);
ALTER TABLE messages ADD CONSTRAINT messages_pkey PRIMARY KEY (created_time);

ALTER TABLE messages ADD CONSTRAINT fk_f_messages_users FOREIGN KEY (from_user) REFERENCES users (username);
ALTER TABLE messages ADD CONSTRAINT fk_t_messages_users FOREIGN KEY (to_user) REFERENCES users (username);

CREATE OR REPLACE FUNCTION notify_changes_chat_messages() RETURNS TRIGGER AS $$
    BEGIN
        PERFORM pg_notify('test', TG_TABLE_NAME);
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER table_messages_changes
    AFTER INSERT OR UPDATE OR DELETE ON messages
    FOR EACH ROW EXECUTE PROCEDURE notify_changes_chat_messages();