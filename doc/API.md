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

**Show timetable**
----
  Returns json data with timetable.

* **URL**

  /timetable?date=yyyy-mm-dd

* **Method:**

  `GET`
  
*  **URL Params**

   **Optional:**
 
   `date=[alphanumeric]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `[`event`, `event`, ..., `event`]`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "User doesn't exist" }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/users/1",
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
  
*  **URL Params**

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

**Show server time**
----
  Returns json data with server current timestamp.

* **URL**

  /time

* **Method:**

  `GET`
  
*  **URL Params**

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
