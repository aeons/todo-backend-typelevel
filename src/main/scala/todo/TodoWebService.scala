package todo

import cats.data.OptionT
import cats.effect.Sync
import cats.implicits._
import io.circe.Decoder
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

case class TodoWebService[F[_]](todoService: TodoService[F])(implicit F: Sync[F]) extends Http4sDsl[F] {
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

      resp.getOrElseF(NotFound())

    case req @ PATCH -> Root / IntVar(id) =>
      val resp =
        for {
          patchTodo   <- OptionT.liftF(req.as[PatchTodo])
          updatedTodo <- todoService.update(TodoId(id), patchTodo)
          resp        <- OptionT.liftF(Ok(updatedTodo.asJson))
        } yield resp

      resp.getOrElseF(NotFound())

    case DELETE -> Root / IntVar(id) =>
      todoService.delete(TodoId(id)) >>
        Ok()

    case DELETE -> Root =>
      todoService.deleteAll >>
        Ok()
  }
}
