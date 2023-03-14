DROP TABLE IF EXISTS users CASCADE;

DROP TABLE IF EXISTS categories CASCADE;

DROP TABLE IF EXISTS events CASCADE;

DROP TABLE IF EXISTS requests CASCADE;

DROP TABLE IF EXISTS compilations CASCADE;

DROP TABLE IF EXISTS compilation_events CASCADE;

CREATE TABLE users (
    id      BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name    VARCHAR(120) NOT NULL,
    email   VARCHAR(120) NOT NULL,

    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users__email UNIQUE (email)
);

CREATE TABLE categories (
    id      BIGINT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    name    VARCHAR(120)    NOT NULL,

    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uq_categories__name UNIQUE (name)
);

CREATE TABLE events (
    id                  BIGINT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    annotation          VARCHAR(2000)   NOT NULL,
    category_id         BIGINT          NOT NULL,
    created_on          TIMESTAMP       NOT NULL,
    description         VARCHAR(7000)   NOT NULL,
    event_date          TIMESTAMP       NOT NULL,
    initiator_id        BIGINT          NOT NULL,
    lat                 NUMERIC(8, 6)   NOT NULL,
    lon                 NUMERIC(9, 6)   NOT NULL,
    paid                BOOLEAN         DEFAULT FALSE,
    participant_limit   INT             DEFAULT 0,
    published_on        TIMESTAMP,
    request_moderation  BOOLEAN         DEFAULT TRUE,
    state               VARCHAR(120)    NOT NULL,
    title               VARCHAR(120)    NOT NULL,

    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT fk_events__categories FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    CONSTRAINT fk_events__users FOREIGN KEY (initiator_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE requests (
    id              BIGINT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    created         TIMESTAMP       NOT NULL,
    event_id        BIGINT          NOT NULL,
    requester_id    BIGINT          NOT NULL,
    status          VARCHAR(120)    NOT NULL,

    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT fk_requests__events FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_requests__users FOREIGN KEY (requester_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uq_requests__event_id__requester_id UNIQUE (event_id, requester_id)
);

CREATE TABLE compilations (
    id      BIGINT          NOT NULL GENERATED ALWAYS AS IDENTITY,
    pinned  BOOLEAN         DEFAULT FALSE,
    title   VARCHAR(120)    NOT NULL,

    CONSTRAINT pk_compilations PRIMARY KEY (id)
);

CREATE TABLE compilation_events (
    compilation_id  BIGINT NOT NULL,
    event_id        BIGINT NOT NULL,

	CONSTRAINT pk_compilation_events PRIMARY KEY (compilation_id, event_id),
	CONSTRAINT fk_compilation_events__compilations FOREIGN KEY (compilation_id) REFERENCES compilations(id) ON DELETE CASCADE,
	CONSTRAINT fk_compilation_events__events FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);
