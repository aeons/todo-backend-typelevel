import io.circe.Encoder
import shapeless.Unwrapped

package object todo {
  implicit def encodeAnyVal[T, U](implicit ev: T <:< AnyVal,
                                  unwrapped: Unwrapped.Aux[T, U],
                                  encoder: Encoder[U]): Encoder[T] =
    Encoder.instance[T](value => encoder(unwrapped.unwrap(value)))
}
