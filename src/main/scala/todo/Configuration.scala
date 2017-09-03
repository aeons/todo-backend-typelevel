package todo

object Configuration {

  object db {
    val driver = "org.postgresql.Driver"
    val url    = "jdbc:postgresql://localhost/postgres"
    val user   = "postgres"
    val pass   = ""
  }

}
