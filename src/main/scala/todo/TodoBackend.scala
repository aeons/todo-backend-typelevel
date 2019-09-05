package todo

import cats.effect._
import org.http4s.implicits._
import org.http4s.server.Server
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.CORS
import todo.config.Configuration

object TodoBackend {

  def start[F[_]: ConcurrentEffect: ContextShift: Timer]: Resource[F, Server[F]] =
    for {
      blocker <- Blocker[F]
      _       <- Resource.liftF(db.migrate[F](Configuration.db))
      xa      <- db.transactor[F](Configuration.db, blocker)
      server <- BlazeServerBuilder[F]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(CORS(TodoRoutes[F](Todos.impl[F](xa)).routes.orNotFound))
        .resource
    } yield server

}
