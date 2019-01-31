# todo-backend

This is an implementation of [Todo-Backend](https://www.todobackend.com/) using
the next generation of releases of the
[Typelevel](https://typelevel.org/) stack of Scala libraries.

The following libraries are used:
- [cats-1.5.0](https://typelevel.org/cats/)
- [fs2-1.0.2](https://fs2.co)
- [http4s-0.20.0-M5](https://http4s.org)
- [circe-0.11.0](https://circe.github.io/circe/)
- [doobie-0.7.0-M2](http://tpolecat.github.io/doobie/)
- [newtype-0.4.2](https://github.com/estatico/scala-newtype)
- [scalaz-deriving-1.0.0](https://github.com/scalaz/scalaz-deriving)
- (and Flyway for database migrations)

# Running tests

To run the default tests for Todo-Backend, you will need a running database.
This can be handled by docker by running the following:

```
docker run --rm -d -p 5432:5432 --name todo-backend-postgres postgres:alpine
```

This project is built using [`mill`](http://www.lihaoyi.com/mill/).

To run the server call `mill todo.run`. To automatically restart the server on code changes, call `mill --watch todo.runBackground`.

Finally, visit [http://www.todobackend.com/specs/index.html?http://localhost:8080/todos/]()
to run the test suite on your local server.
