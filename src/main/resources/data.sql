DROP TABLE IF EXISTS item;

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
VALUES ('Iphone 13', 'apple cellphone', 2500, true),
('Smartwatch Galaxy', 'Smartwatch Samsung Galaxy Watch 3', 1350, true),
('Echo Dot (3ª Generation)', 'Echo Dot (3ª Generation): Smart Speaker with Alexa', 200, false);