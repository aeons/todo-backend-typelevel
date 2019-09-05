import io.estatico.newtype.macros.newtype

package object todo {
  import io.circe.Decoder
  import io.circe.Encoder
  import doobie.util.Meta
  @newtype case class TodoId(asInt: Int)

  object TodoId {
    implicit val meta: Meta[TodoId]       = deriving
    implicit val encoder: Encoder[TodoId] = deriving
    implicit val decoder: Decoder[TodoId] = deriving
  }
}
