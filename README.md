# Sensor reader Backend

## Description
Stores sensor readings read by e.g. Raspberry Pi and serves it restfully.

## Setup
* install JDK8
* install Postres server
* setup Postgres user and db (credentials @ application.properties)
* insert inserts.sql to db
* start server, e.g. by bash:
`./gradlew bootRun`


## Bugs / TODO

* Hibernate java8-time-de-/serialization is not working
* Jackson java8-time-deserialization is not working
* ~~feature: implement JWT/JTWS~~
* TODO: improve spring security configuration
* fix and extend unit and integration tests
* exception handling and error codes for client
* feature: implement compression for dynamic content (e.g. Brotli)
* setup postgres at medhelfer.de
* setup server at medhelfer.de/sensor

