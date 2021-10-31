DROP TABLE IF EXISTS item;

CREATE TABLE item (
      id            INT PRIMARY KEY AUTO_INCREMENT,
      name          VARCHAR(250)  NOT NULL,
      description   VARCHAR(250)  NOT NULL,
      price         INT           NOT NULL,
      views         INT DEFAULT 0 NOT NULL,
      createdAt     TIMESTAMP     NOT NULL,
      updatedAt     TIMESTAMP     NOT NULL
);

INSERT INTO item(name, description, price, views, createdAt, updatedAt)
VALUES ('Iphone 13', 'apple cellphone', 2500, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Smartwatch Galaxy', 'Smartwatch Samsung Galaxy Watch 3', 1350, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Echo Dot (3ª Generation)', 'Echo Dot (3ª Generation): Smart Speaker with Alexa', 200, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
