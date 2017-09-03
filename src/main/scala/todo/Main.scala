package todo

import cats.effect.IO

object Main extends TodoBackend[IO]
