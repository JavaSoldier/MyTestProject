USE `tpdb`;

INSERT INTO Users (userName)
  VALUES
  ('Vasya'),
  ('Petya'),
  ('Kolya'),
  ('Ivan'),
  ('Gleb')
;

INSERT INTO Messages (message, userID, dateTime) VALUES
('hello', '1', ' 2014-05-01 18:19:03'),
('I am fine', '2', ' 2014-05-06 18:19:03'),
('good morning', '3', ' 2014-05-28 18:19:03'),
('I love java too', '4', ' 2014-05-28 18:19:03'),
('I love java', '5', ' 2014-05-11 18:19:03'),
('i love java too)))', '1', ' 2014-05-15 18:19:03'),
('have a rest', '2', ' 2014-05-17 18:19:03'),
('asdasdasd', '3', ' 2014-05-21 18:19:03'),
('hello Ivan', '4', ' 2014-05-25 18:19:03'),
('also i love spring', '5', ' 2014-05-28 18:19:03');
