package todo

import cats.effect.IO
import doobie.specs2._
import doobie.util.transactor.Transactor
import org.specs2.mutable.Specification

object QuerySpec extends Specification with IOChecker {
  val transactor = {
    import Configuration.db
    Transactor.fromDriverManager[IO](db.driver, db.url, db.user, db.pass)
  }

  import Database.queries._

  check(all)
  check(deleteAll)

  check(get(TodoId(0)))
  check(insert("title", Some(100)))
  check(update(TodoId(0), "title", 42, true))
  check(delete(TodoId(0)))

}
