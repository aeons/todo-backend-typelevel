package todo

import cats.data.OptionT
import cats.effect.Effect
import cats.implicits._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

case class TodoRoutes[F[_]: Effect](todos: Todos[F]) extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "todos" =>
      for {
        todos <- todos.getAll
        resp  <- Ok(todos.asJson)
      } yield resp

    case req @ POST -> Root / "todos" =>
      for {
        postTodo <- req.decodeJson[PostTodo]
        todo     <- todos.create(postTodo.title, postTodo.order)
        resp     <- Ok(todo.asJson)
      } yield resp

    case GET -> Root / "todos" / IntVar(id) =>
      val resp = for {
        todo <- todos.get(TodoId(id))
        resp <- OptionT.liftF(Ok(todo.asJson))
      } yield resp

      resp.getOrElse(Response.notFound)

    case req @ PATCH -> Root / "todos" / IntVar(id) =>
      val resp =
        for {
          patchTodo   <- OptionT.liftF(req.decodeJson[PatchTodo])
          updatedTodo <- todos.update(TodoId(id), patchTodo)
          resp        <- OptionT.liftF(Ok(updatedTodo.asJson))
        } yield resp

      resp.getOrElse(Response.notFound)

    case DELETE -> Root / "todos" / IntVar(id) =>
      todos.delete(TodoId(id)) *>
        Ok()

    case DELETE -> Root / "todos" =>
      todos.deleteAll *>
        Ok()
  }
}
