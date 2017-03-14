INSERT INTO types (type_name)
  VALUES
  ('CPU'),
  ('GPU'),
  ('Motherboard'),
  ('RAM'),
  ('Power Supply');

INSERT INTO models (model_name, model_type, release_date)
  VALUES
  ('Intel Core i3', 1, '2014-11-03'),
  ('Intel Core i5', 1, '2014-09-15'),
  ('GTX Titan', 2, '2015-03-10'),
  ('ASUS', 3, '2013-09-15'),
  ('Palit GeForce 450', 2, '2012-10-10');
