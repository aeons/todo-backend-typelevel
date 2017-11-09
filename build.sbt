scalaVersion := "2.12.3"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl"          % "0.18.0-M4",
  "org.http4s" %% "http4s-circe"        % "0.18.0-M4",
  "org.http4s" %% "http4s-blaze-server" % "0.18.0-M4",
  "io.circe"   %% "circe-generic"       % "0.9.0-M1",
  // Database
  "org.tpolecat"   %% "doobie-core"     % "0.5.0-M8",
  "org.tpolecat"   %% "doobie-postgres" % "0.5.0-M8",
  "org.flywaydb"   % "flyway-core"      % "4.2.0",
  // Logging
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  // Testing
  "org.specs2"   %% "specs2-core"   % "4.0.0" % "test",
  "org.tpolecat" %% "doobie-specs2" % "0.5.0-M8"  % "test"
)

fork in run := true
cancelable in Global := true

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:higherKinds"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.patch)
