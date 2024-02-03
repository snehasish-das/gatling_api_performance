package config

import io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class HttpConf{
  def getHttpConfig(): HttpProtocolBuilder={
    http.baseUrl("https://gorest.co.in/public/v2")
      .header("Authorization", "Bearer aa92a19b472f7f5c9ee80a24cb89840fb7a764005c8763891c57705a548c4dae")
      .header("Content-Type", "application/json");
  }

}
