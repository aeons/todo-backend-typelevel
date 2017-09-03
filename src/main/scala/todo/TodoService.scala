package todo

import cats.data.OptionT

trait TodoService[F[_]] {

  def getAll: F[List[Todo]]
  def deleteAll: F[Unit]

  def get(id: TodoId): OptionT[F, Todo]
  def create(title: String, order: Option[Int]): F[Todo]
  def update(id: TodoId, patchTodo: PatchTodo): OptionT[F, Todo]
  def delete(id: TodoId): F[Unit]

}
