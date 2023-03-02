
object CollectionUtils:
  def removeElement[A](n: Int, xs: Seq[A]): Seq[A] = 
    if n < 0 || n >= xs.length || xs.isEmpty then xs
    else 
      val (initial,rest) = xs.splitAt(n)
      initial ++ rest.tail