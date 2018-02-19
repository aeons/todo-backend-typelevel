package todo

import cats._
import cats.data.OptionT
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor
import todo.db.Database

case class DoobieTodoService[F[_]: Monad](xa: Transactor[F]) extends TodoService[F] {

  def getAll: F[List[Todo]] =
    Database.all.transact(xa)

  def deleteAll: F[Unit] =
    Database.deleteAll.transact(xa).void

  def get(id: TodoId): OptionT[F, Todo] =
    OptionT(Database.get(id).transact(xa))

  def create(title: String, order: Option[Int]): F[Todo] =
    Database.insert(title, order).transact(xa)

  def update(id: TodoId, patchTodo: PatchTodo): OptionT[F, Todo] =
    for {
      todo <- OptionT(Database.get(id).transact(xa))
      update = patchTodo.update(todo)
      updatedTodo <- OptionT.liftF(Database.update(update).transact(xa))
    } yield updatedTodo

  def delete(id: TodoId): F[Unit] =
    Database.delete(id).transact(xa).void

}
