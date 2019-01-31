package todo

import cats.effect._

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    TodoBackend.start[IO].use(_ => IO.never)

}
