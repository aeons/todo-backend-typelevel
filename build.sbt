scalaVersion := "2.13.0"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
)

libraryDependencies ++= Seq(
  "org.http4s"  %% "http4s-dsl"          % "0.21.0-M4",
  "org.http4s"  %% "http4s-circe"        % "0.21.0-M4",
  "org.http4s"  %% "http4s-blaze-server" % "0.21.0-M4",
  "io.circe"    %% "circe-derivation"    % "0.12.0-M6",
  "io.estatico" %% "newtype"             % "0.4.3",
  // Database
  "org.tpolecat" %% "doobie-core"     % "0.8.0-RC1",
  "org.tpolecat" %% "doobie-postgres" % "0.8.0-RC1",
  "org.tpolecat" %% "doobie-hikari"   % "0.8.0-RC1",
  "org.flywaydb" % "flyway-core"      % "6.0.1",
  // Logging
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  // Testing
  "org.specs2"   %% "specs2-core"   % "4.7.0"     % "test",
  "org.tpolecat" %% "doobie-specs2" % "0.8.0-RC1" % "test",
)

scalacOptions += "-Ymacro-annotations"

addCompilerPlugin("org.typelevel" %% "kind-projector"  % "0.10.3")

ThisBuild / turbo := true
run / fork := true
Global / cancelable := true
