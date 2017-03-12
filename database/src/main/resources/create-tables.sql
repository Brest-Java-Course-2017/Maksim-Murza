DROP TABLE IF EXISTS types;
DROP TABLE IF EXISTS models;

CREATE TABLE types (
  type_id INT NOT NULL AUTO_INCREMENT,
  type_name VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (type_id)
);

CREATE TABLE models (
  model_id INT NOT NULL AUTO_INCREMENT,
  model_name VARCHAR(255) NOT NULL,
  model_type INT NOT NULL,
  release_date DATE,
  PRIMARY KEY (model_id),
  FOREIGN KEY (model_type) REFERENCES types(type_id)
);