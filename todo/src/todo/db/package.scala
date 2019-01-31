package todo

import cats.effect._
import cats.implicits._
import doobie._
import doobie.hikari._
import org.flywaydb.core.Flyway
import todo.config.DatabaseConfig

package object db {

  def transactor[F[_]: Async: ContextShift](config: DatabaseConfig): Resource[F, Transactor[F]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[F](32)
      te <- ExecutionContexts.cachedThreadPool[F]
      xa <- HikariTransactor.newHikariTransactor[F](
        config.driver,
        config.url,
        config.user,
        config.pass,
        ce,
        te,
      )
    } yield xa

  def migrate[F[_]](config: DatabaseConfig)(implicit F: Sync[F]): F[Unit] =
    F.delay {
      Flyway.configure().dataSource(config.url, config.user, config.pass).load().migrate()
      ()
    }

}
