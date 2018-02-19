package todo

import cats.effect.IO
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends TodoBackend[IO]
