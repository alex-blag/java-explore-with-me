INSERT INTO users (name, email)
VALUES
    ('John Smith', 'john.smith@mail.com')
    , ('Jane Rose', 'jane.rose@mail.com');

INSERT INTO categories (name)
VALUES
    ('Ballet')
    , ('Trampolining');

INSERT INTO locations (name, lat, lon)
VALUES (
    'Walley'
    , 51.348530
    , -2.610440
);

INSERT INTO events (
    annotation
    , category_id
    , created_on
    , description
    , event_date
    , initiator_id
    , location_id
    , paid
    , participant_limit
    , published_on
    , request_moderation
    , state
    , title
) VALUES (
    'Trampolining in white shorts'
    , 2
    , '2023-02-17 12:20:00'
    , 'Trampolining in white shorts and boots'
    , '2023-02-20 12:00:00'
    , 1
    , 1
    , TRUE
    , 10
    , NULL
    , TRUE
    , 'PENDING'
    , 'Trampolining'
);
