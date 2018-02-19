package todo

import cats.data.OptionT
import cats.effect.Effect
import cats.implicits._
import io.circe.Decoder
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

case class TodoWebService[F[_]](todoService: TodoService[F])(implicit F: Effect[F]) extends Http4sDsl[F] {
  implicit def circeJsonDecoder[A: Decoder]: EntityDecoder[F, A] = jsonOf[F, A]

  val service: HttpService[F] = HttpService[F] {
    case GET -> Root =>
      for {
        todos <- todoService.getAll
        resp  <- Ok(todos.asJson)
      } yield resp

    case req @ POST -> Root =>
      for {
        postTodo <- req.as[PostTodo]
        todo     <- todoService.create(postTodo.title, postTodo.order)
        resp     <- Ok(todo.asJson)
      } yield resp

    case GET -> Root / IntVar(id) =>
      val resp = for {
        todo <- todoService.get(TodoId(id))
        resp <- OptionT.liftF(Ok(todo.asJson))
      } yield resp

      resp.getOrElse(Response.notFound)

    case req @ PATCH -> Root / IntVar(id) =>
      val resp =
        for {
          patchTodo   <- OptionT.liftF(req.as[PatchTodo])
          updatedTodo <- todoService.update(TodoId(id), patchTodo)
          resp        <- OptionT.liftF(Ok(updatedTodo.asJson))
        } yield resp

      resp.getOrElse(Response.notFound)

    case DELETE -> Root / IntVar(id) =>
      todoService.delete(TodoId(id)) *>
        Ok()

    case DELETE -> Root =>
      todoService.deleteAll *>
        Ok()
  }
}
