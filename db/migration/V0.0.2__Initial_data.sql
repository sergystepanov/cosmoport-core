--
-- Initial core data
--
-- @encoding UTF-8
--

INSERT INTO LOCALE (id, code, is_default, locale_description) VALUES
  (1, 'en', 1, 'English'),
  (2, 'ru', 0, 'Русский'),
  (3, 'el', 0, 'Ελληνική');

INSERT INTO I18N (id, tag, external, description, params) VALUES
  (1, 'ui_caption_gate', 1, 'Interface caption for gate.', ''),
  (2, 'ui_caption_type', 1, 'Interface caption for type.', ''),
  (3, 'ui_caption_destination', 1, '', ''),
  (4, 'ui_caption_status', 1, '', ''),
  (5, 'ui_caption_time_etd', 1, '', ''),
  (6, 'ui_caption_next_event', 1, '', ''),
  (7, 'ui_months_names', 1, '', 'json_array');

INSERT INTO TRANSLATION (i18n_id, locale_id, tr_text) VALUES
  (1, 1, 'Gate'),
  (1, 2, 'Ворота'),
  (1, 3, 'Πύλη'),
  (2, 1, 'Type'),
  (2, 2, 'Тип'),
  (2, 3, 'Τύπος'),
  (3, 1, 'Destination'),
  (3, 2, 'Назначение'),
  (3, 3, 'Προορισμός'),
  (4, 1, 'Status'),
  (4, 2, 'Статус'),
  (4, 3, 'Κατάσταση'),
  (5, 1, 'Time to ETD'),
  (5, 2, 'Время прибытия'),
  (5, 3, 'Ωρα άφιξης'),
  (6, 1, 'Next event'),
  (6, 2, 'Далее'),
  (6, 3, 'επόμενος'),
  (7, 1, '["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Dec"]'),
  (7, 2, '["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"]'),
  (7, 3, '["Ιαν", "Φεβ", "Μάρ", "Απρ", "Μάι", "Ιούν", "Ιούλ", "Αύγ", "Σεπ", "Οκτ", "Νοέ", "Δεκ"]');

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
