package todo

import cats._
import cats.data.OptionT
import cats.effect.Bracket
import cats.implicits._
import doobie._
import doobie.implicits._
import todo.db.Database

trait Todos[F[_]] {

  def getAll: F[List[Todo]]
  def deleteAll: F[Unit]

  def get(id: TodoId): OptionT[F, Todo]
  def create(title: String, order: Option[Int]): F[Todo]
  def update(id: TodoId, patchTodo: PatchTodo): OptionT[F, Todo]
  def delete(id: TodoId): F[Unit]

}

object Todos {

  def impl[F[_]: Monad: Bracket[?[_], Throwable]](xa: Transactor[F]): Todos[F] = new Todos[F] {

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

}
