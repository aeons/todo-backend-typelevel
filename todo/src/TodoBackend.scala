package todo

import cats.effect._
import fs2.StreamApp.ExitCode
import fs2._
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.CORS
import todo.config.Configuration

import scala.concurrent.ExecutionContext

class TodoBackend[F[_]](implicit F: Effect[F], ec: ExecutionContext) extends StreamApp[F] {

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
