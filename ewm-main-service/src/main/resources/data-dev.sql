INSERT INTO users (name, email)
VALUES
    ('John Smith', 'john.smith@mail.com'),
    ('Jane Rose', 'jane.rose@mail.com');

INSERT INTO categories (name)
VALUES
    ('Ballet'),
    ('Trampolining');

INSERT INTO events (
    annotation,
    category_id,
    created_on,
    description,
    event_date,
    initiator_id,
    lat,
    lon,
    paid,
    participant_limit,
    published_on,
    request_moderation,
    state,
    title
)
VALUES
(
    'Trampolining in white shorts',
    2,
    '2023-02-17 12:20:00',
    'Trampolining in white shorts and boots',
    '2023-02-20 12:00:00',
    1,
    55.75,
    37.62,
    TRUE,
    10,
    NULL,
    TRUE,
    'PENDING',
    'Trampolining'
);
