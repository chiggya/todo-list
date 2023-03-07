import cats.effect.*

import IOFunctions.*
import Commands.*

object TodoMain extends IOApp:
  def run(args: List[String]): IO[ExitCode] = 
    loop.as(ExitCode.Success)

  def loop: IO[Unit] =
    for
      _ <- prompt
      command <- getLine.map(parse)
      _ <- if command == Quit then IO.unit
           else evaluateCommand(command) >> loop
    yield ()
