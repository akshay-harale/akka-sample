package com.lightbend.akka.sample

import java.util.Date

import akka.actor.{Actor, Kill, PoisonPill, Props, Terminated}
import com.datastax.driver.core.{Cluster, Session}

/**
  * Created by akshayh on 7/3/18.
  */
class QueryExecutor(session:Session) extends Actor{

  override def receive: Receive = {
    case query:String=>
      //TODO execute cassandra query
      try{

        val startTime: Long = new Date().getTime
        println(startTime)
        //session.execute(query)
        val endTime: Long = new Date().getTime
        println(s"totla time in millis: for query: $query :-> " +(endTime-startTime))
        println("------------------------------------------------------------------")
      }catch {
        case ex:Exception=>println("Exception:"+ex.getMessage)
      }
    self ! PoisonPill
    case Terminated=>
      println("Poisen pill")
    case _=>
      println("Wrong query")
  }
}

object QueryExecutor{
  def props(session:Session): Props = Props(classOf[QueryExecutor],session)
}