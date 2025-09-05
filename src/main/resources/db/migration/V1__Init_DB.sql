-- SEQUENCES
CREATE SEQUENCE social_user_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE message_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE post_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE post_comment_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE post_like_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE subscription_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE image_seq START WITH 1 INCREMENT BY 50;

-- TABLES
CREATE TABLE social_user
(
    id       INTEGER PRIMARY KEY DEFAULT nextval('social_user_seq'),
    name     VARCHAR(20)  NOT NULL,
    surname  VARCHAR(20)  NOT NULL,
    email    VARCHAR(30)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL
);

CREATE TABLE image
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('image_seq'),
    file_path VARCHAR(512) NOT NULL
);

CREATE TABLE post
(
    id        INTEGER PRIMARY KEY    DEFAULT nextval('post_seq'),
    user_id   INTEGER       NOT NULL,
    post_text VARCHAR(1000) NOT NULL,
    post_date TIMESTAMP     NOT NULL DEFAULT now(),
    image_id  INTEGER       NOT NULL
);

CREATE TABLE post_comment
(
    id           INTEGER PRIMARY KEY    DEFAULT nextval('post_comment_seq'),
    post_id      INTEGER       NOT NULL,
    user_id      INTEGER       NOT NULL,
    comment_text VARCHAR(1000) NOT NULL,
    comment_date TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE TABLE post_like
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('post_like_seq'),
    post_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL
);

CREATE TABLE message
(
    id           INTEGER PRIMARY KEY    DEFAULT nextval('message_seq'),
    sender_id    INTEGER       NOT NULL,
    receiver_id  INTEGER       NOT NULL,
    message_text VARCHAR(1000) NOT NULL,
    message_date TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE TABLE subscription
(
    id            INTEGER PRIMARY KEY DEFAULT nextval('subscription_seq'),
    subscriber_id INTEGER   NOT NULL,
    target_id     INTEGER   NOT NULL,
    subscribed_at TIMESTAMP NOT NULL  DEFAULT now(),
    CONSTRAINT ck_subscription_not_self CHECK (subscriber_id <> target_id)
);

-- FKs + CASCADE
ALTER TABLE message
    ADD CONSTRAINT fk_message_sender_id FOREIGN KEY (sender_id) REFERENCES social_user (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_message_receiver_id FOREIGN KEY (receiver_id) REFERENCES social_user (id) ON DELETE CASCADE;

ALTER TABLE post
    ADD CONSTRAINT fk_post_user_id FOREIGN KEY (user_id) REFERENCES social_user (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_post_image_id FOREIGN KEY (image_id) REFERENCES image (id) ON DELETE CASCADE;

ALTER TABLE post_comment
    ADD CONSTRAINT fk_post_comment_post_id FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_post_comment_user_id FOREIGN KEY (user_id) REFERENCES social_user (id) ON DELETE CASCADE;

ALTER TABLE post_like
    ADD CONSTRAINT fk_post_like_post_id FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_post_like_user_id FOREIGN KEY (user_id) REFERENCES social_user (id) ON DELETE CASCADE;

ALTER TABLE subscription
    ADD CONSTRAINT fk_subscription_subscriber_id FOREIGN KEY (subscriber_id) REFERENCES social_user (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_subscription_target_id FOREIGN KEY (target_id) REFERENCES social_user (id) ON DELETE CASCADE;

-- UNIQUEs
ALTER TABLE social_user
    ADD CONSTRAINT uk_social_user_email UNIQUE (email);
ALTER TABLE image
    ADD CONSTRAINT uk_image_file_path UNIQUE (file_path);
ALTER TABLE post_like
    ADD CONSTRAINT uk_post_like_unique UNIQUE (post_id, user_id);
ALTER TABLE subscription
    ADD CONSTRAINT uk_subscription_pair UNIQUE (subscriber_id, target_id);

-- CHECKs
ALTER TABLE social_user
    ADD CONSTRAINT ck_social_user_role CHECK (role IN ('ROLE_USER', 'ROLE_ADMIN'));

-- INDEXes
CREATE INDEX idx_post_user_id ON post (user_id);
CREATE INDEX idx_post_date ON post (post_date);
CREATE INDEX idx_post_comment_post_id ON post_comment (post_id);
CREATE INDEX idx_post_comment_user_id ON post_comment (user_id);
CREATE INDEX idx_post_like_post_id ON post_like (post_id);
CREATE INDEX idx_post_like_user_id ON post_like (user_id);
CREATE INDEX idx_subscription_subscriber_id ON subscription (subscriber_id);
CREATE INDEX idx_subscription_target_id ON subscription (target_id);
CREATE INDEX idx_message_dialog ON message (sender_id, receiver_id, message_date);

-- OWNED BY
ALTER SEQUENCE social_user_seq OWNED BY social_user.id;
ALTER SEQUENCE message_seq OWNED BY message.id;
ALTER SEQUENCE post_seq OWNED BY post.id;
ALTER SEQUENCE post_comment_seq OWNED BY post_comment.id;
ALTER SEQUENCE post_like_seq OWNED BY post_like.id;
ALTER SEQUENCE subscription_seq OWNED BY subscription.id;
ALTER SEQUENCE image_seq OWNED BY image.id;
