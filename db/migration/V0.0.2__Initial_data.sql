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
  (7, 'ui_months_names', 1, '', 'json_array'),
  (8, 'event_type_name_1', 0, '', ''),
  (9, 'event_type_name_2', 0, '', ''),
  (10, 'event_type_name_3', 0, '', ''),
  (11, 'event_type_sub_name_1', 0, '', ''),
  (12, 'event_type_sub_name_2', 0, '', ''),
  (13, 'event_type_sub_name_3', 0, '', ''),
  (14, 'event_dest_name_1', 0, '', ''),
  (15, 'event_dest_name_2', 0, '', ''),
  (16, 'event_dest_name_3', 0, '', ''),
  (17, 'event_dest_name_4', 0, '', ''),

  (18, 'event_status_name_1', 0, '', ''),
  (19, 'event_status_name_2', 0, '', ''),
  (20, 'event_status_name_3', 0, '', ''),
  (21, 'event_status_name_4', 0, '', ''),
  (22, 'event_status_name_5', 0, '', ''),
  (23, 'event_status_name_6', 0, '', ''),
  (24, 'event_status_name_7', 0, '', '');

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
  (7, 3, '["Ιαν", "Φεβ", "Μάρ", "Απρ", "Μάι", "Ιούν", "Ιούλ", "Αύγ", "Σεπ", "Οκτ", "Νοέ", "Δεκ"]'),
  (8, 1, 'Excursion'),
  (8, 2, 'Экскурсия'),
  (8, 3, 'Εκδρομή'),
  (9, 1, 'Masterclass'),
  (9, 2, 'Мастеркласс'),
  (9, 3, 'σεμινάρια'),
  (10, 1, 'Mission'),
  (10, 2, 'Миссия'),
  (10, 3, 'Αποστολή'),
  (11, 1, 'Station lunch'),
  (11, 2, 'Запуск станции'),
  (11, 3, 'Σταθμός μεσημεριανό'),
  (12, 1, 'Xenobiology'),
  (12, 2, 'Ксенобиология'),
  (12, 3, 'Xenoβιολογία'),
  (13, 1, 'To the edge of the universe'),
  (13, 2, 'На край вселенной'),
  (13, 3, 'Στην άκρη του σύμπαντος'),
  (14, 1, 'Moon'),
  (14, 2, 'Луна'),
  (14, 3, 'Φεγγάρι'),
  (15, 1, 'Around the Earth'),
  (15, 2, 'Вокруг Земли'),
  (15, 3, 'Γύρω από τη Γη'),
  (16, 1, 'Mars'),
  (16, 2, 'Марс'),
  (16, 3, 'Άρης'),
  (17, 1, 'Jupiter'),
  (17, 2, 'Юпитер'),
  (17, 3, 'Ζεύς'),
  (18, 1, 'Opened'),
  (18, 2, 'Открыт'),
  (18, 3, 'Άνοιξε'),
  (19, 1, 'Closed'),
  (19, 2, 'Закрыт'),
  (19, 3, 'Κλειστό'),
  (20, 1, 'Canceled'),
  (20, 2, 'Отменен'),
  (20, 3, 'Ακυρώθηκε'),
  (21, 1, 'Boarding'),
  (21, 2, 'Посадка'),
  (21, 3, 'επιβίβασης'),
  (22, 1, 'Departed'),
  (22, 2, 'Отправлен'),
  (22, 3, 'Περασμένος'),
  (23, 1, 'Returned'),
  (23, 2, 'Вернулся'),
  (23, 3, 'επέστρεψαν'),
  (24, 1, 'Preorder'),
  (24, 2, 'Предзаказ'),
  (24, 3, 'Προπαραγγελία');

INSERT INTO EVENT_TYPE (id, i18n_event_type_name, i18n_event_type_subname, event_type_description,
                        default_duration, default_repeat_interval) VALUES
  (1, 8, 11, '', 40, 5),
  (2, 9, 12, '', 90, 0),
  (3, 10, 13, '', 120, 0),
  (4, 10, 11, '', 120, 0);

INSERT INTO EVENT_STATUS (id, i18n_status) VALUES
  (1, 18), (2, 19), (3, 20), (4, 21), (5, 22), (6, 23), (7, 24);

INSERT INTO EVENT_DESTINATION (id, i18n_event_destination_name) VALUES
  (1, 14), (2, 15), (3, 16), (4, 17);

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
  ('2017-02-05', 2, 6, 1, 1, 540, 30, 20, 10, 1),
  ('2017-02-05', 1, 1, 2, 1, 570, 80, 30, 10, 2),
  ('2017-02-05', 3, 4, 3, 1, 600, 45, 45, 10, 3),
  ('2017-02-05', 2, 1, 4, 1, 660, 120, 12, 10, 4),
  ('2017-02-05', 4, 1, 1, 1, 750, 90, 45, 10, 5),
  ('2017-02-05', 4, 3, 2, 1, 840, 30, 67, 10, 6),
  ('2017-02-05', 2, 7, 3, 1, 900, 80, 23, 10, 7),
  ('2017-02-05', 1, 1, 4, 1, 1005, 45, 45, 10, 8),
  ('2017-02-05', 4, 1, 1, 1, 1020, 120, 68, 10, 9),
  ('2017-02-05', 1, 1, 2, 1, 1080, 90, 34, 10, 10);
