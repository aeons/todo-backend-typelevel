package todo

import cats.effect._
import cats.implicits._
import doobie.util.transactor.Transactor
import org.flywaydb.core.Flyway
import todo.config.DatabaseConfig

package object db {

  def init[F[_]: Async](config: DatabaseConfig): F[Transactor[F]] =
    migrate[F](config) *> transactor[F](config)

  def transactor[F[_]](config: DatabaseConfig)(implicit F: Async[F]): F[Transactor[F]] =
    F.delay(Transactor.fromDriverManager[F](config.driver, config.url, config.user, config.pass))

  def migrate[F[_]](config: DatabaseConfig)(implicit F: Sync[F]): F[Unit] =
    F.delay {
      val flyway = new Flyway
      flyway.setDataSource(config.url, config.user, config.pass)
      val _ = flyway.migrate()
    }

}
