package simulations.gorest

import config.HttpConf
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef.{jsonPath, _}
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class gorestUsers extends Simulation{
  val http_conf = new HttpConf().getHttpConfig()

  def getUser(userId:String) = {
    http("Get user")
      .get("/users/"+userId)
      .check(status is (200))
  }

  def createUser() ={
    http("Create new User")
      .post("/users")
      .body(RawFileBody("./src/test/resources/payloads/gorestCreateUser.json")).asJson
      .check(status in (200 to 210))
  }

  def updateUser(userId:String)={
    http("Update User")
      .patch("/users/"+userId)
      .body(RawFileBody("./src/test/resources/payloads/gorestUpdateUser.json")).asJson
  }
  def deleteUser(userId:String)={
    http("Delete user")
      .delete("/users/"+userId)
  }

  val scn = scenario("Create User")
    .exec(createUser().check(jsonPath("$.id").saveAs("userId")))
    .pause(3000.milliseconds)
    .exec(getUser("${userId}").check(jsonPath("$.name").is("Snehasish")))
    .pause(1,5)
    .exec(updateUser("${userId}").check(jsonPath("$.email").not("tentative012@gmail.com")))
    .pause(3)
    .exec(deleteUser("${userId}"))

  //execution
  setUp(scn.inject(atOnceUsers(1))).protocols(http_conf)
}
