package todo

import io.circe._
import io.circe.Codec
import io.circe.derivation.deriveCodec

case class Todo(
    id: TodoId,
    title: String,
    order: Int,
    completed: Boolean,
)
object Todo {

  implicit val _encoder: Encoder[Todo] =
    Encoder.forProduct4("title", "order", "completed", "url") { todo =>
      (todo.title, todo.order, todo.completed, s"http://localhost:8080/todos/${todo.id.asInt}")
    }

}

case class PostTodo(
    title: String,
    order: Option[Int],
)

object PostTodo {
  implicit val codec: Codec[PostTodo] = deriveCodec
}

case class PatchTodo(
    title: Option[String],
    order: Option[Int],
    completed: Option[Boolean],
) {

  def update(base: Todo): Todo =
    base.copy(
      title = title.getOrElse(base.title),
      order = order.getOrElse(base.order),
      completed = completed.getOrElse(base.completed),
    )

}

object PatchTodo {
  implicit val codec: Codec[PatchTodo] = deriveCodec
}
