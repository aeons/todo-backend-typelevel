scalaVersion := "2.12.3"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl"          % "0.18.0-SNAPSHOT",
  "org.http4s" %% "http4s-circe"        % "0.18.0-SNAPSHOT",
  "org.http4s" %% "http4s-blaze-server" % "0.18.0-SNAPSHOT",
  "io.circe"   %% "circe-generic"       % "0.9.0-M1",
  // Database
  "org.tpolecat"   %% "doobie-core"     % "0.5.0-M6",
  "org.tpolecat"   %% "doobie-postgres" % "0.5.0-M6",
  "org.postgresql" % "postgresql"       % "42.1.4",
  "org.flywaydb"   % "flyway-core"      % "4.2.0",
  // Logging
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

fork in run := true
cancelable in Global := true

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:higherKinds"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.patch)
