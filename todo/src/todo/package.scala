import io.circe.Encoder
import shapeless.Unwrapped

package object todo {
  implicit def encodeAnyVal[T, U](implicit ev: T <:< AnyVal,
                                  unwrapped: Unwrapped.Aux[T, U],
                                  encoder: Encoder[U]): Encoder[T] = {
    val _ = ev // We are actually using it
    Encoder.instance[T](value => encoder(unwrapped.unwrap(value)))
  }
}
