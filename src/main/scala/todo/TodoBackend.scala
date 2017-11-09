package todo

import cats._
import cats.effect._
import fs2._
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.CORS
import org.http4s.util.StreamApp
import org.http4s.util.StreamApp.ExitCode
import todo.config.Configuration

class TodoBackend[F[_]](implicit F: Effect[F]) extends StreamApp[F] {

  override def stream(args: List[String], requestShutdown: F[Unit]): Stream[F, ExitCode] =
    for {
      transactor  <- Stream.eval(db.init(Configuration.db))
      todoService <- Stream.eval(F.delay(DoobieTodoService(transactor)))
      server <- BlazeBuilder[F]
        .bindHttp()
        .mountService(CORS(TodoWebService[F](todoService).service), "/todos/")
        .serve
    } yield server

}
