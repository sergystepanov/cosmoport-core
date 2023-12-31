--
-- Initial core data
--
-- @encoding UTF-8
--

INSERT INTO LOCALE (id, code, is_default, locale_description, show, show_time)
VALUES (1, 'en', 1, 'English', 1, 15),
       (2, 'ru', 0, 'Русский', 1, 10),
       (3, 'el', 0, 'Ελληνική', 1, 10);

INSERT INTO I18N (id, tag, external, description, params)
VALUES (1, 'ui_caption_gate', 1, 'Interface caption for gate.', ''),
       (2, 'ui_caption_type', 1, 'Interface caption for type.', ''),
       (3, 'ui_caption_destination', 1, '', ''),
       (4, 'ui_caption_status', 1, '', ''),
       (5, 'ui_caption_time_etd', 1, '', ''),
       (6, 'ui_caption_next_event', 1, '', ''),
       (7, 'ui_months_names', 1, '', 'json_array'),
       (8, 'event_type_category_1', 0, '', ''),
       (9, 'event_type_category_2', 0, '', ''),
       (10, 'event_type_category_3', 0, '', ''),
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
       (24, 'event_status_name_7', 0, '', ''),
       (25, 'event_status_name_8', 0, '', ''),

       (26, 'ui_caption_duration', 1, '', ''),
       (27, 'ui_caption_hours', 1, '', ''),
       (28, 'ui_caption_minutes', 1, '', ''),
       (29, 'event_type_description_1', 0, '', ''),
       (30, 'ui_caption_departing', 1, '', ''),
       (31, 'ui_caption_cost', 1, '', ''),
       (32, 'event_type_subcategory_r1cu08leg20i', 0, '', ''),
       (33, 'event_type_name_r1cu08leg20i_1', 0, '', ''),
       (34, 'event_type_description_r1cu08leg20i_1', 0, '', ''),
       (35, 'event_type_name_r1cu08leg20i_2', 0, '', ''),
       (36, 'event_type_description_r1cu08leg20i_2', 0, '', ''),
       (37, 'event_type_name_r1cu08leg20i_3', 0, '', ''),
       (38, 'event_type_description_r1cu08leg20i_3', 0, '', '');

INSERT INTO TRANSLATION (i18n_id, locale_id, tr_text)
VALUES (1, 1, 'Gate'),
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
       (7, 1, '["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]'),
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
       (23, 1, 'Return'),
       (23, 2, 'Возвращается'),
       (23, 3, 'Επιστροφές'),
       (24, 1, 'Returned'),
       (24, 2, 'Вернулся'),
       (24, 3, 'επέστρεψαν'),
       (25, 1, 'Preorder'),
       (25, 2, 'Предзаказ'),
       (25, 3, 'Προπαραγγελία'),
       (26, 1, 'Duration'),
       (26, 2, 'Продолжительность'),
       (26, 3, 'Διάρκεια'),
       (27, 1, 'h'),
       (27, 2, 'ч.'),
       (27, 3, 'ώ'),
       (28, 1, 'm'),
       (28, 2, 'м.'),
       (28, 3, 'λ'),
       (29, 1,
        'You will find a fascinating journey of discovery and a real space station, you''ll learn how it works from the inside, what mechanisms and systems are needed for full protection of human life in space. You will find a fascinating journey of discovery and a real space station.'),
       (29, 2,
        'Вас ждёт увлекательное и познавательное путешествие на настоящую космическую станцию, Вы узнаете как она работает изнутри, какие механизмы и системы необходимы для полного обеспечения жизни человека в космосе. Вас ждёт увлекательное и познавательное путешествие на настоящую космическую станцию.'),
       (29, 3,
        'Θα βρείτε ένα συναρπαστικό ταξίδι ανακάλυψης και ένα πραγματικό διαστημικό σταθμό, θα μάθετε πώς λειτουργεί από μέσα, ποιους μηχανισμούς και τα συστήματα που απαιτούνται για την πλήρη προστασία της ανθρώπινης ζωής στο διάστημα. Θα βρείτε ένα συναρπαστικό ταξίδι ανακάλυψης και ένα πραγματικό διαστημικό σταθμό.'),
       (30, 1, 'Departing'),
       (30, 2, 'Отправление'),
       (30, 3, 'Αναχωρούν'),
       (31, 1, 'Cost'),
       (31, 2, 'Цена'),
       (31, 3, 'Κόστος'),
       (32, 1, 'Piloting lessons'),
       (32, 2, 'Обучение пилотированию'),
       (32, 3, 'Μαθήματα πιλότου'),
       (33, 1, 'Lesson 1: Introduction'),
       (33, 2, 'Урок 1: Введение'),
       (33, 3, 'Μάθημα 1: Εισαγωγή'),
       (34, 1, 'This quick lesson will provide you with some basic information you need to get started.'),
       (34, 2, 'Этот быстрый урок предоставит вам некоторую базовую информацию, необходимую для начала работы.'),
       (34, 3, 'Αυτό το γρήγορο μάθημα θα σας δώσει μερικές βασικές πληροφορίες που χρειάζεστε για να ξεκινήσετε.'),
       (35, 1, 'Lesson 2: Basic training'),
       (35, 2, 'Урок 2: Базовый трейнинг'),
       (35, 3, 'Μάθημα 2: Βασική εκπαίδευση'),
       (36, 1, 'Learn to fly! Here''s what you need to know before taking your first flight lesson.'),
       (36, 2, 'Учиться летать! Вот что вам нужно знать, прежде чем взять первый урок полета.'),
       (36, 3, 'Μαθαίνω να πετάω! Εδώ είναι τι πρέπει να γνωρίζετε πριν κάνετε το πρώτο σας μάθημα πτήσης.'),
       (37, 1, 'Lesson 3: Advanced training'),
       (37, 2, 'Урок 3: Продвинутая подготовка'),
       (37, 3, 'Μάθημα 3: Προχωρημένη εκπαίδευση'),
       (38, 1,
        'This training designed to enable flight crews to respond to challenging situations and achieve the highest level of safety, while developing solid flying skills, swift and accurate decisions, and precise crew communication.'),
       (38, 2,
        'Это обучение предназначено для того, чтобы летные экипажи могли реагировать на сложные ситуации и достигать высочайшего уровня безопасности, одновременно развивая прочные летные навыки, быстрые и точные решения и четкое общение с экипажем.'),
       (38, 3,
        'Αυτή η εκπαίδευση έχει σχεδιαστεί για να επιτρέπει στα πληρώματα πτήσης να ανταποκρίνονται σε δύσκολες καταστάσεις και να επιτυγχάνουν το υψηλότερο επίπεδο ασφάλειας, αναπτύσσοντας ταυτόχρονα ισχυρές πτητικές δεξιότητες, γρήγορες και ακριβείς αποφάσεις και ακριβή επικοινωνία του πληρώματος.');

INSERT INTO EVENT_TYPE_CATEGORY (id, i18n_event_type_category_name, parent)
VALUES (1, 8, null),
       (2, 9, null),
       (3, 10, null),
       (4, 32, 2);

INSERT INTO EVENT_TYPE (id, category_id, i18n_event_type_name, i18n_event_type_description, default_duration,
                        default_repeat_interval)
VALUES (1, 1, 11, 29, 40, 5),
       (2, 2, 12, 29, 90, 0),
       (3, 3, 13, 29, 120, 0),
       (4, 3, 11, 29, 120, 0),
       (5, 4, 33, 34, 300, 0),
       (6, 4, 35, 36, 300, 0),
       (7, 4, 37, 38, 300, 0);

INSERT INTO EVENT_STATE (id, i18n_state)
VALUES (1, 18),
       (2, 19);

INSERT INTO EVENT_STATUS (id, i18n_status)
VALUES (1, 20),
       (2, 21),
       (3, 22),
       (4, 23),
       (5, 24),
       (6, 25);

INSERT INTO EVENT_DESTINATION (id, i18n_event_destination_name)
VALUES (1, 14),
       (2, 15),
       (3, 16),
       (4, 17);

INSERT INTO GATE (id, number, gate_name)
VALUES (1, 1, 'Main'),
       (2, 2, ''),
       (3, 3, ''),
       (4, 4, ''),
       (5, 5, ''),
       (6, 6, ''),
       (7, 7, ''),
       (8, 8, ''),
       (9, 9, ''),
       (10, 10, '');

INSERT INTO TIMETABLE (event_date, event_type_id, event_status_id, event_state_id, event_destination_id, gate_id,
                       gate2_id, start_time,
                       duration_time, cost, people_limit, contestants)
VALUES ('2017-02-05', 2, 1, 1, 1, 1, 1, 540, 30, 20, 10, 1),
       ('2017-02-05', 1, NULL, 1, 2, 1, 1, 580, 90, 30, 10, 2),
       ('2017-02-05', 3, NULL, 1, 3, 1, 1, 600, 45, 45, 10, 3),
       ('2017-02-05', 2, NULL, 1, 4, 1, 1, 660, 120, 12, 10, 4),
       ('2017-02-05', 4, NULL, 1, 1, 1, 1, 750, 80, 45, 10, 5),
       ('2017-02-05', 4, 1, 1, 2, 1, 1, 840, 30, 67, 10, 6),
       ('2017-02-05', 2, 2, 1, 3, 1, 1, 900, 80, 23, 10, 7),
       ('2017-02-05', 1, NULL, 1, 4, 1, 1, 1005, 45, 45, 10, 8),
       ('2017-02-05', 4, NULL, 1, 1, 1, 2, 1020, 120, 68, 10, 9),
       ('2017-02-05', 1, NULL, 1, 2, 1, 2, 1080, 90, 34, 10, 10);

INSERT INTO SETTINGS (param, value)
VALUES ('timetable_screen_lines', '20'),
       ('boarding_time', '5'),
       ('password', 'Abcd1234!'),
       ('sync_server_address', 'http://sync.cosmoport.local'),
       ('sync_server_key', 'HH3y=cD9dzXaT876'),
       ('sync_server_on', 'off'),
       ('business_hours',
        '{"hours":[{"day":"mon","start":0,"end":0,"non":false},{"day":"tue","start":0,"end":0,"non":false},{"day":"wed","start":0,"end":0,"non":false},{"day":"thu","start":0,"end":0,"non":false},{"day":"fri","start":0,"end":0,"non":false},{"day":"sat","start":0,"end":0,"non":true},{"day":"sun","start":0,"end":0,"non":true}]}');


