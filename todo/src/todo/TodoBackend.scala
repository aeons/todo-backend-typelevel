package todo

import cats.effect._
import fs2._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.CORS
import todo.config.Configuration

object TodoBackend {

  def stream[F[_]: ConcurrentEffect: ContextShift]: Stream[F, ExitCode] =
    for {
      _     <- Stream.eval(db.migrate[F](Configuration.db))
      xa    <- Stream.resource(db.transactor[F](Configuration.db))
      todos <- Stream(Todos.impl[F](xa))
      exitCode <- BlazeServerBuilder[F]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(CORS(TodoRoutes[F](todos).routes.orNotFound))
        .serve
    } yield exitCode

}
