DROP TABLE IF EXISTS service_app CASCADE;

DROP TABLE IF EXISTS endpoint_hits CASCADE;

CREATE TABLE service_app (
    id          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    name        CHARACTER VARYING(100) NOT NULL,

    CONSTRAINT pk_service_app PRIMARY KEY (id),
    CONSTRAINT uc_service_app UNIQUE (name)
);

CREATE TABLE endpoint_hits (
    id          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    app_id      BIGINT NOT NULL,
    uri         CHARACTER VARYING(100) NOT NULL,
    ip          CHARACTER VARYING(15) NOT NULL,
    time_stamp  TIMESTAMP NOT NULL,

    CONSTRAINT pk_endpoint_hits PRIMARY KEY (id),
    CONSTRAINT fk_endpoint_hits__service_app FOREIGN KEY (app_id) REFERENCES service_app(id) ON DELETE CASCADE
);
