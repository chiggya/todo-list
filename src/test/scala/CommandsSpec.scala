import org.scalatest.flatspec.AnyFlatSpec

class CommandsSpec extends AnyFlatSpec:
  import Commands.*

  "parsing a string" should "give the corresponding command" in {

    assert(parse("add buy milk") == Add("buy milk"))
    assert(parse("rm 2") == Remove("2"))
    assert(parse("v") == View)
    assert(parse("view") == View)
    assert(parse("q") == Quit)
    assert(parse("quit") == Quit)
    assert(parse("") == Unknown)
    assert(parse("quite") == Unknown)
    assert(parse("addbuy milk") == Unknown)
  }
