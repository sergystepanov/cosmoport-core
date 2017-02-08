--
-- Initial core data
--
-- @encoding UTF-8
--

INSERT INTO EVENT_TYPE (id, event_type_name, event_type_subname, event_type_description,
                        default_duration, default_repeat_interval) VALUES
  (1, 'Excursion', 'Station launch', '', 40, 5),
  (2, 'Masterclass', 'Xenobiology', '', 90, 0),
  (3, 'Mission', 'To the edge of the universe', '', 120, 0),
  (4, 'Mission', 'Station launch', '', 120, 0);

INSERT INTO EVENT_STATUS (id, status) VALUES
  (1, 'opened'), (2, 'closed'), (3, 'canceled'), (4, 'boarding'), (5, 'departed'),
  (7, 'returned'), (8, 'preorder');

INSERT INTO EVENT_DESTINATION (id, event_destination_name) VALUES
  (1, 'Moon'),
  (2, 'Around the Earth'),
  (3, 'Mars'),
  (4, 'Jupiter');

INSERT INTO GATE (id, number, gate_name) VALUES
  (1, 1, ''),
  (2, 2, ''),
  (3, 3, ''),
  (4, 4, ''),
  (5, 5, ''),
  (6, 6, ''),
  (7, 7, ''),
  (8, 8, ''),
  (9, 9, ''),
  (10, 10, '');

INSERT INTO TIMETABLE (event_date, event_type_id, event_status_id, event_destination_id, gate_id, start_time,
  duration_time, cost, people_limit, contestants) VALUES
  ('2017-02-05', 2, 7, 1, 1, 540, 30, 20, 10, 1),
  ('2017-02-05', 1, 1, 2, 1, 570, 80, 30, 10, 2),
  ('2017-02-05', 3, 4, 3, 1, 600, 45, 45, 10, 3),
  ('2017-02-05', 2, 1, 4, 1, 660, 120, 12, 10, 4),
  ('2017-02-05', 4, 1, 1, 1, 750, 90, 45, 10, 5),
  ('2017-02-05', 4, 3, 2, 1, 840, 30, 67, 10, 6),
  ('2017-02-05', 2, 9, 3, 1, 900, 80, 23, 10, 7),
  ('2017-02-05', 1, 1, 4, 1, 1005, 45, 45, 10, 8),
  ('2017-02-05', 4, 1, 1, 1, 1020, 120, 68, 10, 9),
  ('2017-02-05', 1, 1, 2, 1, 1080, 90, 34, 10, 10);
