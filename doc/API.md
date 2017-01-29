# Cosmoport core API
> version 1 (0.0.1)

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

### Endpoints
- events

    Gets all events for today (from 0:00 to 23:59)
    
    ***Request:***
    ```text
    GET /events
    ```
    ***Response:***
    ```text
    HTTP/1.1 200 OK
    [{event 1}, {event 2}, ... {event n}]
    ```
    
    Gets all events from a day in format (yyyy-mm-dd)  

    ***Request:***
    ```text
    GET /events?for=2017-01-01
    ```
    ***Response:***
    ```text
    HTTP/1.1 200 OK
    [{event 1}, {event 2}, ... {event n}]
    ```
    
    Adds a new event for today's schedule

    ***Request:***
    ```text
    POST /event
    {event}
    ```
    ***Responses:***   
    ```text
    HTTP/1.1 200 OK ...
    ```
    ```text
    HTTP/1.1 400 Bad Request
    {"code": 001, "message": "Duplicate event time"}
    ```
    
    Sets a whole day schedule of events for a time period (***replaces all data***)
    
    ***Request:***
    ```text
    POST /events?from=2017-01-01&to=2017-01-10
    ```
    ***Responses:***   
    ```text
    HTTP/1.1 200 OK ...
    ```
    ```text
    HTTP/1.1 400 Bad Request
    {"code": 002, "message": "Invalid input period"}
    ```
    ```text
    HTTP/1.1 500 Internal Server Error
    {"code": 003, "message": "Server internal error"}
    ```

- event types
    Gets all event types
    ***Request:***
    ```text
    GET /event/types
    ```
    ***Response:***
    ```text
    HTTP/1.1 200 OK
    [{event_type 1}, {event_type 2}, ... {event_type n}]
    ```

- time and date
    ```text
    GET /time → {timestamp: 1387330857}
    ```