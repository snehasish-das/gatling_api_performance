package simulations.reqres

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class AddUser extends Simulation {
  //Configuration
  val http_conf = http.baseUrl("https://reqres.in")
    .header("Accept", "application/json")
    .header("Content-Type", "application/json")

  //Scenario
  val scn = scenario("Add user")
    .exec(http("Add user request")
      .post("/api/users")
      .body(RawFileBody("./src/test/resources/payloads/putUser.json")).asJson
      .check(status is 201)
    )
    .pause(3)
    .exec(
      http("get user request")
        .get("/api/users/2")
        .check(status is 200)
    )

  //Executions
  setUp(scn.inject(atOnceUsers(1))).protocols(http_conf)
}
