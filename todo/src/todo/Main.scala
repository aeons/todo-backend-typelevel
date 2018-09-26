package todo

import cats.effect._
import cats.implicits._

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    TodoBackend.stream[IO].compile.drain.as(ExitCode.Success)

}
