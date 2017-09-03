package todo

import cats.effect.IO
import doobie.specs2._
import doobie.util.transactor.Transactor
import org.specs2.mutable.Specification
import todo.config.Configuration
import todo.db.Database.queries._

object QuerySpec extends Specification with IOChecker {

  val transactor: Transactor[IO] = db.init[IO](Configuration.db).unsafeRunSync()

  check(all)
  check(deleteAll)

  check(get(TodoId(0)))
  check(insert("title", Some(100)))
  check(update(TodoId(0), "title", 42, true))
  check(delete(TodoId(0)))

}
