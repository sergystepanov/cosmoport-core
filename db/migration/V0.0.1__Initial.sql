--
-- Initial core schema
--
-- @encoding UTF-8
--

--
-- Stores scheduled events of the timetable
--
CREATE TABLE TIMETABLE (
  id             INTEGER        NOT NULL PRIMARY KEY                                          AUTOINCREMENT,
  departure_time INTEGER        NOT NULL                                                      DEFAULT (0),
  type           TEXT           NOT NULL                                                      DEFAULT (''),
  duration       INTEGER        NOT NULL                                                      DEFAULT (1),
  destination    TEXT           NOT NULL                                                      DEFAULT (''),
  cost           DECIMAL(10, 2) NOT NULL                                                      DEFAULT (1.00),
  status         TEXT           NOT NULL CHECK (
    status IN ('pending', 'opened', 'closed', 'canceled', 'boarding', 'departed', 'back', 'preorder',
               'inactive'))                                                                   DEFAULT ('pending'),
  gate_no        INTEGER        NOT NULL                                                      DEFAULT (0),
  passengers_max INTEGER        NOT NULL                                                      DEFAULT (1),
  bought         INTEGER        NOT NULL CHECK (bought <= passengers_max)                     DEFAULT (0),
  date_added     TIMESTAMP      NOT NULL                                                      DEFAULT (CURRENT_TIMESTAMP)
);

--
-- SQLite performance tuning
--
PRAGMA foreign_keys = ON;