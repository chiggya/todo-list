import org.scalatest.flatspec.AnyFlatSpec

class CollectionUtilsSpec extends AnyFlatSpec:
  import CollectionUtils.*
  "removing an element an element at a given index within range" should "return a sequence with one fewer element if non empty" in {
    val xs = Seq(1,2,3)
    val ys: Seq[Int] = Seq()
    assert(removeElement(1,xs) == Seq(1,3))
    assert(removeElement(5,xs) == xs)
    assert(removeElement(0, ys) == ys)

  }
