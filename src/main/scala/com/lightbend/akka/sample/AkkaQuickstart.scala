package com.lightbend.akka.sample

import akka.actor.ActorSystem
import com.datastax.driver.core.{Cluster, Session}
object AkkaQuickstart extends App {
  val system: ActorSystem = ActorSystem("helloAkka")
  val queryList=List("Query1","Query2","Query3","Query4","Query5","Query6","Query7")

  val session: Session = try {
    Cluster.builder
      .addContactPoint("locahost")
      .withPort(9042)
      .build
      .connect()
  } catch {
    case ex: Exception =>
      println("Exception:" + ex.getMessage)
      null
  }
  queryList.map(q=>{
    system.actorOf(QueryExecutor.props(session)) ! q
  })
}