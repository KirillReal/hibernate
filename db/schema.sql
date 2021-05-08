CREATE TABLE engine(
                       id SERIAL PRIMARY KEY,
                       model VARCHAR(255) NOT NULL
);

CREATE TABLE car(
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    engine_id INT NOT NULL UNIQUE REFERENCES engine(id)
);

CREATE TABLE driver(
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255)
);

CREATE TABLE history_owner(
                              id SERIAL PRIMARY KEY,
                              driver_id INT NOT NULL REFERENCES driver(id),
                              car_id INT NOT NULL REFERENCES car(id)
)