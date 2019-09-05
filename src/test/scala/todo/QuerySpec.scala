package todo

import cats.effect._
import doobie.specs2._
import doobie.util.transactor.Transactor
import org.specs2.mutable.Specification
import scala.concurrent.ExecutionContext
import todo.config.Configuration
import todo.db.Database.queries._

class QuerySpec extends Specification with IOChecker {

  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  db.migrate[IO](Configuration.db).unsafeRunSync()

  val transactor: Transactor[IO] = Transactor.fromDriverManager[IO](
    Configuration.db.driver,
    Configuration.db.url,
    Configuration.db.user,
    Configuration.db.pass,
  )

  check(all)
  check(deleteAll)

  check(get(TodoId(0)))
  check(insert("", None))
  check(update(TodoId(0), "", 0, false))
  check(delete(TodoId(0)))

}
