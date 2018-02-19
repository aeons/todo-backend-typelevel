# todo-backend

This is an implementation of [Todo-Backend](https://www.todobackend.com/) using
the next generation of releases of the
[Typelevel](https://typelevel.org/) stack of Scala libraries.

The following libraries are used:
- [cats-1.0.1](https://typelevel.org/cats/)
- [fs2-0.10.1](https://fs2.co)
- [http4s-0.18.0](https://http4s.org)
- [circe-0.9.1](https://circe.github.io/circe/)
- [doobie-0.5.0](http://tpolecat.github.io/doobie/)
- (and Flyway for database migrations)

# Running tests

To run the default tests for Todo-Backend, you will need a running database.
This can be handled by docker by running the following:

```
docker run --rm -d -p 5432:5432 --name todo-backend-postgres postgres:10
```

This project is built using [`mill`](http://www.lihaoyi.com/mill/).

To run the server call `mill todo.run`.

Finally, visit [http://www.todobackend.com/specs/index.html?http://localhost:8080/todos/]()
to run the test suite on your local server.
