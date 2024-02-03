package simulations.gorest

import config.HttpConf
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class gorestUsers extends Simulation{
  val http_conf = new HttpConf().getHttpConfig();
  val scn = scenario("Create User")
    .exec(
      http("Create new User")
        .post("/users")
        .body(RawFileBody("./src/test/resources/payloads/gorestCreateUser.json")).asJson
        .check(status in(200 to 210))
        .check(jsonPath("$.id").saveAs("userId"))
    )
    .pause(3000.milliseconds)
    .exec(
      http("Get user")
        .get("/users/${userId}")
        .check(status is(200))
        .check(jsonPath("$.name").is("Snehasish"))
    )

  //execution
  setUp(scn.inject(atOnceUsers(1))).protocols(http_conf)
}
