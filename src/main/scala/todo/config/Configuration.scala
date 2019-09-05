package todo.config

case class DatabaseConfig(
    driver: String,
    url: String,
    user: String,
    pass: String,
)

object Configuration {

  val db = DatabaseConfig(
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://localhost/postgres",
    user = "postgres",
    pass = "",
  )

}
