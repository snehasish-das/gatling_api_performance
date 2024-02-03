package simulations.reqres

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class GetUser extends Simulation{

  //Configuration
  val http_conf = http.baseUrl("https://reqres.in")
    .header("Accept","application/json")
    .header("Content-Type", "application/json")

  //Scenario
  val scn = scenario("get user")
    .exec(http("get user request")
      .get("/api/users/2")
      .check(status is 200)
    )

  //Execution
  setUp(scn.inject(atOnceUsers(1))).protocols(http_conf)

}