package todo

import doobie._
import doobie.implicits._

object Database {
  def all: ConnectionIO[List[Todo]] =
    queries.all.list

  def get(id: TodoId): ConnectionIO[Option[Todo]] =
    sql"select * from todos where id = $id".query[Todo].option

  def insert(title: String, order: Option[Int]): ConnectionIO[Todo] =
    queries
      .insert(title, order)
      .withUniqueGeneratedKeys[Todo]("id", "title", "ordering", "completed")

  def update(todo: Todo): ConnectionIO[Todo] =
    queries
      .update(todo.id, todo.title, todo.order, todo.completed)
      .withUniqueGeneratedKeys[Todo]("id", "title", "ordering", "completed")

  def delete(id: TodoId): ConnectionIO[Int] =
    queries.delete(id).run

  def deleteAll: ConnectionIO[Int] =
    queries.deleteAll.run

  object queries {
    object fs {
      val select = fr"select * from todos"
      val delete = fr"delete from todos"

      def whereId(id: TodoId) = fr"where id = $id"
    }

    val all       = fs.select.query[Todo]
    val deleteAll = fs.delete.update

    def get(id: TodoId) = (fs.select ++ fs.whereId(id)).query[Todo]

    def insert(title: String, order: Option[Int]) =
      sql"""insert into todos (title, ordering, completed)
          values ($title, ${order.getOrElse(0)}, false)""".update

    def update(id: TodoId, title: String, order: Int, completed: Boolean) =
      (fr"""update todos set
            title = $title,
            ordering = $order,
            completed = $completed""" ++ fs.whereId(id)).update

    def delete(id: TodoId) = (fs.delete ++ fs.whereId(id)).update
  }
}
