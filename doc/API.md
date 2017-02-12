# Core API (Cosmoport)
> version 1 (0.1.0)

API version 1 uses HTTP/1.1 protocol and JSON type transport.


### Objects

an event object (“event”)
```text
{"id": 1, value: "value"}
```

an event type object (“event_type”)
```text
{}
```

an error object (“error”)
```text
{"code": 000, "message": "a message"}
```


**General error responses**
----

* **Code:** 500 INTERNAL SERVER ERROR <br />
  **Content:** `{"code": 003, "message": "Server internal error"}`


**Get timetable events**
----
  Returns json data with timetable.

* **URL**

  /timetable?date=yyyy-mm-dd

* **Method:**

  `GET`

* **URL Params**

   **Optional:**

   `date=[alphanumeric]`

   filter timetable events by a date

   `gate=[number]`

   filter timetable events by a gate id

* **Data Params**

    None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `[`event`, `event`, ..., `event`]`

* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{"code": 004, "message": "Wrong filter params."}`

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/timetable?gate=1",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```


**Create new event for timetable**
----
  Creates new event for timetable.

* **URL**

  /timetable?date=yyyy-mm-dd

* **Method:**

  `POST`

* **URL Params**

   **Required:**

   `date=[alphanumeric]`

* **Data Params**

  **Required:**

  `event=[`event`]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{id: 123}`

* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{"code": 001, "message": "Wrong input params."}`

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{"code": 002, "message": "Duplicate event time."}`


**Get server time**
----
  Returns json data with the current server timestamp.

* **URL**

  /time

* **Method:**

  `GET`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{timestamp: 1387330857}`

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/time",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```

**Get current translations**
---
  Returns current data with translation values.

* **URL**

  /translations

* **Method:**

  `GET`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{
                     "ru":{
                        "11":{
                           "id":32,
                           "values":[
                              "Запуск станции"
                           ]
                        },
                        "22":{
                           "id":65,
                           "values":[
                              "Отправлен"
                           ]
                        },
                        "12":{
                           "id":35,
                           "values":[
                              "Ксенобиология"
                           ]
                        },
                        "23":{
                           "id":68,
                           "values":[
                              "Вернулся"
                           ]
                        },
                        "13":{
                           "id":38,
                           "values":[
                              "На край вселенной"
                           ]
                        },
                        "24":{
                           "id":71,
                           "values":[
                              "Предзаказ"
                           ]
                        },
                        "ui_caption_gate":{
                           "id":2,
                           "values":[
                              "Ворота"
                           ]
                        },
                        "14":{
                           "id":41,
                           "values":[
                              "Луна"
                           ]
                        },
                        "15":{
                           "id":44,
                           "values":[
                              "Вокруг Земли"
                           ]
                        },
                        "16":{
                           "id":47,
                           "values":[
                              "Марс"
                           ]
                        },
                        "ui_caption_next_event":{
                           "id":17,
                           "values":[
                              "Далее"
                           ]
                        },
                        "17":{
                           "id":50,
                           "values":[
                              "Юпитер"
                           ]
                        },
                        "ui_caption_status":{
                           "id":11,
                           "values":[
                              "Статус"
                           ]
                        },
                        "18":{
                           "id":53,
                           "values":[
                              "Открыт"
                           ]
                        },
                        "19":{
                           "id":56,
                           "values":[
                              "Закрыт"
                           ]
                        },
                        "ui_caption_type":{
                           "id":5,
                           "values":[
                              "Тип"
                           ]
                        },
                        "ui_caption_destination":{
                           "id":8,
                           "values":[
                              "Назначение"
                           ]
                        },
                        "8":{
                           "id":23,
                           "values":[
                              "Экскурсия"
                           ]
                        },
                        "ui_caption_time_etd":{
                           "id":14,
                           "values":[
                              "Время прибытия"
                           ]
                        },
                        "9":{
                           "id":26,
                           "values":[
                              "Мастеркласс"
                           ]
                        },
                        "ui_months_names":{
                           "id":20,
                           "values":[
                              "Янв",
                              "Фев",
                              "Мар",
                              "Апр",
                              "Май",
                              "Июн",
                              "Июл",
                              "Авг",
                              "Сен",
                              "Окт",
                              "Ноя",
                              "Дек"
                           ]
                        },
                        "20":{
                           "id":59,
                           "values":[
                              "Отменен"
                           ]
                        },
                        "10":{
                           "id":29,
                           "values":[
                              "Миссия"
                           ]
                        },
                        "21":{
                           "id":62,
                           "values":[
                              "Посадка"
                           ]
                        }
                     },
                     "el":{
                        "11":{
                           "id":33,
                           "values":[
                              "Σταθμός μεσημεριανό"
                           ]
                        },
                        "22":{
                           "id":66,
                           "values":[
                              "Περασμένος"
                           ]
                        },
                        "12":{
                           "id":36,
                           "values":[
                              "Xenoβιολογία"
                           ]
                        },
                        "23":{
                           "id":69,
                           "values":[
                              "επέστρεψαν"
                           ]
                        },
                        "13":{
                           "id":39,
                           "values":[
                              "Στην άκρη του σύμπαντος"
                           ]
                        },
                        "24":{
                           "id":72,
                           "values":[
                              "Προπαραγγελία"
                           ]
                        },
                        "ui_caption_gate":{
                           "id":3,
                           "values":[
                              "Πύλη"
                           ]
                        },
                        "14":{
                           "id":42,
                           "values":[
                              "Φεγγάρι"
                           ]
                        },
                        "15":{
                           "id":45,
                           "values":[
                              "Γύρω από τη Γη"
                           ]
                        },
                        "16":{
                           "id":48,
                           "values":[
                              "Άρης"
                           ]
                        },
                        "ui_caption_next_event":{
                           "id":18,
                           "values":[
                              "επόμενος"
                           ]
                        },
                        "17":{
                           "id":51,
                           "values":[
                              "Ζεύς"
                           ]
                        },
                        "ui_caption_status":{
                           "id":12,
                           "values":[
                              "Κατάσταση"
                           ]
                        },
                        "18":{
                           "id":54,
                           "values":[
                              "Άνοιξε"
                           ]
                        },
                        "19":{
                           "id":57,
                           "values":[
                              "Κλειστό"
                           ]
                        },
                        "ui_caption_type":{
                           "id":6,
                           "values":[
                              "Τύπος"
                           ]
                        },
                        "ui_caption_destination":{
                           "id":9,
                           "values":[
                              "Προορισμός"
                           ]
                        },
                        "8":{
                           "id":24,
                           "values":[
                              "Εκδρομή"
                           ]
                        },
                        "ui_caption_time_etd":{
                           "id":15,
                           "values":[
                              "Ωρα άφιξης"
                           ]
                        },
                        "9":{
                           "id":27,
                           "values":[
                              "σεμινάρια"
                           ]
                        },
                        "ui_months_names":{
                           "id":21,
                           "values":[
                              "Ιαν",
                              "Φεβ",
                              "Μάρ",
                              "Απρ",
                              "Μάι",
                              "Ιούν",
                              "Ιούλ",
                              "Αύγ",
                              "Σεπ",
                              "Οκτ",
                              "Νοέ",
                              "Δεκ"
                           ]
                        },
                        "20":{
                           "id":60,
                           "values":[
                              "Ακυρώθηκε"
                           ]
                        },
                        "10":{
                           "id":30,
                           "values":[
                              "Αποστολή"
                           ]
                        },
                        "21":{
                           "id":63,
                           "values":[
                              "επιβίβασης"
                           ]
                        }
                     },
                     "en":{
                        "11":{
                           "id":31,
                           "values":[
                              "Station lunch"
                           ]
                        },
                        "22":{
                           "id":64,
                           "values":[
                              "Departed"
                           ]
                        },
                        "12":{
                           "id":34,
                           "values":[
                              "Xenobiology"
                           ]
                        },
                        "23":{
                           "id":67,
                           "values":[
                              "Returned"
                           ]
                        },
                        "13":{
                           "id":37,
                           "values":[
                              "To the edge of the universe"
                           ]
                        },
                        "24":{
                           "id":70,
                           "values":[
                              "Preorder"
                           ]
                        },
                        "ui_caption_gate":{
                           "id":1,
                           "values":[
                              "Gate"
                           ]
                        },
                        "14":{
                           "id":40,
                           "values":[
                              "Moon"
                           ]
                        },
                        "15":{
                           "id":43,
                           "values":[
                              "Around the Earth"
                           ]
                        },
                        "16":{
                           "id":46,
                           "values":[
                              "Mars"
                           ]
                        },
                        "ui_caption_next_event":{
                           "id":16,
                           "values":[
                              "Next event"
                           ]
                        },
                        "17":{
                           "id":49,
                           "values":[
                              "Jupiter"
                           ]
                        },
                        "ui_caption_status":{
                           "id":10,
                           "values":[
                              "Status"
                           ]
                        },
                        "18":{
                           "id":52,
                           "values":[
                              "Opened"
                           ]
                        },
                        "19":{
                           "id":55,
                           "values":[
                              "Closed"
                           ]
                        },
                        "ui_caption_type":{
                           "id":4,
                           "values":[
                              "Type"
                           ]
                        },
                        "ui_caption_destination":{
                           "id":7,
                           "values":[
                              "Destination"
                           ]
                        },
                        "8":{
                           "id":22,
                           "values":[
                              "Excursion"
                           ]
                        },
                        "ui_caption_time_etd":{
                           "id":13,
                           "values":[
                              "Time to ETD"
                           ]
                        },
                        "9":{
                           "id":25,
                           "values":[
                              "Masterclass"
                           ]
                        },
                        "ui_months_names":{
                           "id":19,
                           "values":[
                              "Jan",
                              "Feb",
                              "Mar",
                              "Apr",
                              "May",
                              "Jun",
                              "Jul",
                              "Aug",
                              "Sep",
                              "Nov",
                              "Dec"
                           ]
                        },
                        "20":{
                           "id":58,
                           "values":[
                              "Canceled"
                           ]
                        },
                        "10":{
                           "id":28,
                           "values":[
                              "Mission"
                           ]
                        },
                        "21":{
                           "id":61,
                           "values":[
                              "Boarding"
                           ]
                        }
                     }
                  }`

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/translations",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```