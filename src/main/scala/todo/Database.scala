package todo

import doobie._
import doobie.implicits._

object Database {
  def all: ConnectionIO[List[Todo]] =
    sql"select * from todos".query[Todo].list

  def get(id: TodoId): ConnectionIO[Option[Todo]] =
    sql"select * from todos where id = $id".query[Todo].option

  def insert(title: String, order: Option[Int]): ConnectionIO[Todo] =
    sql"""insert into todos (title, ordering, completed)
          values ($title, ${order.getOrElse(0)}, false)""".update
      .withUniqueGeneratedKeys[Todo]("id", "title", "ordering", "completed")

  def update(todo: Todo): ConnectionIO[Todo] = {
    import todo._
    sql"""update todos set
            title = $title,
            ordering = $order,
            completed = $completed
            where id = $id""".update
      .withUniqueGeneratedKeys[Todo]("id", "title", "ordering", "completed")
  }

  def delete(id: TodoId): ConnectionIO[Int] =
    sql"delete from todos where id = $id".update.run

  def deleteAll: ConnectionIO[Int] =
    sql"delete from todos".update.run

}
