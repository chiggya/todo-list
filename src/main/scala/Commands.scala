
sealed trait Commands
final case class Add(command: String) extends Commands
final case class Remove(index: String) extends Commands
case object View extends Commands
case object Quit extends Commands
case object Unknown extends Commands

object Commands:

  def parse(s: String): Commands = s match
    case s"add ${task}" => Add(task)
    case s"rm ${index}" => Remove(index)
    case "v" | "view" => View
    case "q" | "quit" => Quit
    case _ => Unknown

