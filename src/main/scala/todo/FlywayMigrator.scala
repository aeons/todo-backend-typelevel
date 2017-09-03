package todo

import cats.effect.Sync
import org.flywaydb.core.Flyway

object FlywayMigrator {
  def migrate[F[_]](implicit F: Sync[F]): F[Unit] =
    F.delay {
      val flyway = new Flyway
      flyway.setDataSource(Configuration.db.url, Configuration.db.user, Configuration.db.pass)
      val _ = flyway.migrate()
    }
}
