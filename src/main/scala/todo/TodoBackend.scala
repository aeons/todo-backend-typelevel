package todo

import cats._
import cats.effect._
import cats.implicits._
import doobie.util.transactor.Transactor
import fs2._
import org.http4s.MaybeResponse
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.CORS
import org.http4s.util.StreamApp

class TodoBackend[F[_]](implicit F: Effect[F], S: Semigroup[F[MaybeResponse[F]]])
    extends StreamApp[F] {
  import Configuration._

  val mkTransactor: F[Transactor[F]] =
    F.delay(Transactor.fromDriverManager[F](db.driver, db.url, db.user, db.pass))

  override def stream(args: List[String], requestShutdown: F[Unit]): Stream[F, Nothing] =
    for {
      _           <- Stream.eval(FlywayMigrator.migrate[F])
      todoService <- Stream.eval(mkTransactor.map(xa => TodoService(xa)))
      server <- BlazeBuilder[F]
        .bindHttp()
        .mountService(CORS(TodoWebService[F](todoService).service))
        .serve
    } yield server

}
