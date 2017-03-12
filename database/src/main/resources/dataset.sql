INSERT INTO types (type_id, type_name)
  VALUES
  (1, 'CPU'),
  (2, 'GPU'),
  (3, 'Motherboard'),
  (4, 'RAM'),
  (5, 'Power Supply');

INSERT INTO models (model_id, model_name, model_type, release_date)
  VALUES
  (1, 'Intel Core i3', 1, '2014-11-03'),
  (2, 'Intel Core i5', 1, '2014-09-15'),
  (3, 'GTX Titan', 2, '2015-03-10'),
  (4, 'ASUS', 3, '2013-09-15'),
  (5, 'Palit GeForce 450', 2, '2012-10-10');
