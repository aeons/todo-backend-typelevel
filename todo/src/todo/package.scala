import doobie._
import io.circe.{Decoder, Encoder}
import io.estatico.newtype.macros.newtype

package object todo {

  @newtype
  @scalaz.deriving(Decoder, Encoder, Meta)
  case class TodoId(asInt: Int)

}
