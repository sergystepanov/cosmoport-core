# Cosmoport Core

> The application core app

[API](./doc/API.md)

### Uses

- Java 21 (LTS)
- Spring Boot 3
- SQLite (DB)
- Flyway (DB migrations)

### Default settings

- Host: **127.0.0.1** (localhost)
- Port: **8081**

### Run

To run the application you should execute the following script:

```text
gradlew run
```

### Distribute

For the portable app distribution:

```text
gradlew release
```

The `build/install/core` folder will contain the resulting files, including the automatically downloaded JRE (Java) for
the current system.

For the generic app distribution use:

```text
gradlew distTar | distZip | installDist
```

See the result in the `build` folder.
In order to run the app, use `bin/core-x.x.x` executable file.
You can specify a custom JRE installation folder for the bin scripts with the `JAVA_HOME` env variable.

### Database

Execute `gradlew flywayMigrate` if you want to recreate the SQLite database `db/core0.db`.
