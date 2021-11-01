
DROP TABLE if exists item cascade;
DROP TABLE if exists views cascade;


CREATE TABLE item (
      id            INT PRIMARY KEY AUTO_INCREMENT,
      name          VARCHAR(250)  NOT NULL,
      description   VARCHAR(250)  NOT NULL,
      price         INT           NOT NULL,
      active        BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE views (
      id            INT PRIMARY KEY AUTO_INCREMENT,
      item_id       INT                  NOT NULL,
      created_at    TIMESTAMP   DEFAULT  NOT NULL
);

ALTER TABLE views ADD CONSTRAINT fk_view_item_constraint
    FOREIGN KEY (item_id)
    REFERENCES item(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

INSERT INTO item(name, description, price, active)
VALUES ('Item 01 Name', 'Item 01 Description', 200, true),
       ('Item 02 Name', 'Item 02 Description', 50, false),
       ('Item 03 Name', 'Item 03 Description', 135, true),
       ('Item 04 Name', 'Item 04 Description', 500, true);

INSERT INTO views(item_id, created_at)
VALUES  (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (1L, CURRENT_TIMESTAMP),
        (3L, CURRENT_TIMESTAMP),
        (3L, CURRENT_TIMESTAMP),
        (3L, CURRENT_TIMESTAMP),
        (4L, CURRENT_TIMESTAMP - INTERVAL 1 HOUR),
        (4L, CURRENT_TIMESTAMP - INTERVAL 2 HOUR),
        (4L, CURRENT_TIMESTAMP),
        (4L, CURRENT_TIMESTAMP),
        (4L, CURRENT_TIMESTAMP),
        (4L, CURRENT_TIMESTAMP),
        (4L, CURRENT_TIMESTAMP),
        (4L, CURRENT_TIMESTAMP),
        (4L, CURRENT_TIMESTAMP);