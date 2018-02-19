package todo

import io.circe._
import io.circe.generic.JsonCodec

@JsonCodec case class TodoId(id: Int) extends AnyVal

case class Todo(
    id: TodoId,
    title: String,
    order: Int,
    completed: Boolean
) {
  val url: String = s"http://localhost:8080/todos/${id.id}"
}

object Todo {

  implicit val encodeTodo: Encoder[Todo] =
    Encoder.forProduct4("title", "order", "completed", "url") { todo =>
      (todo.title, todo.order, todo.completed, todo.url)
    }

}

@JsonCodec case class PostTodo(
    title: String,
    order: Option[Int]
)

@JsonCodec case class PatchTodo(
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
