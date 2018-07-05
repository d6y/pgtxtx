package pgtxtx

object Main {
  def main(args: Array[String]): Unit =
    args match {
      case Array(filename) => (read _ andThen interpret)(filename)
      case _ => Console.err.println("Expected CSV filename as an argument")
    }

  case class Row(name: String, text: String)
  type Error = kantan.csv.ReadError

  def interpret(results: List[Either[Error,Row]]): Unit = 
    results.foreach(interpret)

  def interpret(result: Either[Error,Row]): Unit =
    result match {
      case Left(err) => Console.err.println(s"Warning: $err")
      case Right(row) => write(row.name, expand(row.text))
    }

  def expand(text: String): String =
    text.drop(1).replaceAll("\\\\n", "\n").replaceAll("\\\\\"","\"").init

  def read(filename: String): List[Either[Error,Row]] = {
    import java.io.File
    import kantan.csv._ 
    import kantan.csv.ops._
    import kantan.csv.generic._
    new File(filename).asCsvReader[Row](rfc).toList
  }

  def write(filename: String, text: String): Unit = {
    import java.io.{File, PrintWriter}
    val pw = new PrintWriter(new File(s"${filename}.txt"))
    try pw.write(text) finally pw.close()
  }

}
