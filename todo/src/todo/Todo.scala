package todo

import io.circe._

case class Todo(
    id: TodoId,
    title: String,
    order: Int,
    completed: Boolean
)
object Todo {

  implicit val encodeTodo: Encoder[Todo] =
    Encoder.forProduct4("title", "order", "completed", "url") { todo =>
      (todo.title, todo.order, todo.completed, s"http://localhost:8080/todos/${todo.id.asInt}")
    }

}

@scalaz.deriving(Decoder)
case class PostTodo(
    title: String,
    order: Option[Int]
)

@scalaz.deriving(Decoder)
case class PatchTodo(
    title: Option[String],
    order: Option[Int],
    completed: Option[Boolean]
) {

  def update(base: Todo): Todo =
    base.copy(
      title = title.getOrElse(base.title),
      order = order.getOrElse(base.order),
      completed = completed.getOrElse(base.completed)
    )

}
