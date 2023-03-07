
import cats.effect.*
import cats.implicits.*
import scala.io.*
import java.io.File
import java.io.FileWriter

object IOFunctions:
  
  def getLine: IO[String] = IO(StdIn.readLine)

  def prompt: IO[Unit] = 
    IO.println{
      """ Commands   |
        |-----------------------------------
        | add <task> - add task to list
        | rm  <num>  - remove task by number
        | v          - view all tasks
        | q          - quit
        """.stripMargin
    }

  object ReadIO:
    private def sourceIO: IO[Source] = IO(Source.fromFile(("todo-list.dat")))
    private def closeSource(s: Source): IO[Unit] = IO(s.close())
    private def readLines(s: Source): IO[Seq[String]] = IO(s.getLines.toList)
    private val makeResourceForRead: Resource[IO, Source] =
      Resource.make(sourceIO)(src => closeSource(src))
    def useReadResource: IO[Seq[String]] = 
      makeResourceForRead.use{src => readLines(src)}
    
  object WriteIO:
    private def makeResourceForWriter(append: Boolean): Resource[IO,FileWriter] = 
      Resource.make(
        IO.blocking(new FileWriter(new File("todo-list.dat"), append))
      )(fw => IO(fw.close()))
    def writeToFile(str: String, append: Boolean): IO[Unit] =
      makeResourceForWriter(append).use{ writer =>
        IO(writer.write(str + "\n"))
      }

  /** handle input received from user */

  def evaluateCommand(command: Commands): IO[Unit] = command match
    case Add(c) =>
      add(c) >> IO.println("") >> view >> IO.println("")
    case Remove(i) =>
      remove(i) >> IO.println("") >> view >> IO.println("")
    case View =>
      view
    case Unknown =>
      unknown
    
  
  /** append task to file */

  private def add(c: String): IO[Unit] =
    
    WriteIO.writeToFile(c, true)
    
    
  /** remove task from file (list 0 based)  */

  private def remove(i: String): IO[Unit] =
    import CollectionUtils.*
    val n = i.toIntOption
    if n == None then evaluateCommand(Unknown)
    else
      for
        currentTasks <- ReadIO.useReadResource
        augmentedTasks <- IO(removeElement(n.get - 1, currentTasks))
        tasksAsString <- IO(augmentedTasks.mkString("\n"))
        _ <- WriteIO.writeToFile(tasksAsString, false)
      yield ()

  /** print a numbered list of tasks */

  private def view: IO[Unit] = 
    val items = ReadIO.useReadResource.map(_.zip(LazyList.from(1)))
    for
      xs <- items
      _ <- xs.traverse((str,i) => IO.println(s"$i $str"))
    yield ()

  /** respond to unexpected input */

  private def unknown: IO[Unit] = 
    IO.println("Unknown command")


extension [A](a: IO[A])
  def debugs: IO[A] =
    a.map{ aa => println(s"[${Thread.currentThread.getName} $aa]"); aa}