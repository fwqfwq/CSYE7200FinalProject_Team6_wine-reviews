//package Akka
//
//import akka.actor.AbstractActor.Receive
//import akka.actor.TypedActor.{context, self}
//import akka.actor.{ActorSystem, Props}
//import akka.actor.{Actor, ActorRef}
//
//import scala.io.StdIn
//
//
//class Actor {
//
//}
//class SC1(val sc:ActorRef) extends Actor {
//   def receive: Receive = {
//    case "start" => {
//      println("start")
//      sc ! "reply for start"
//    }
//    case "end" => {
//      println("end")
//      sc ! "reply for end"
//    }
//    case "stop" => {
//      context.stop(self)
//      context.system.terminate() // shut down ActorSystem（ExcutorService）
//    }
//  }
//}
//
//
//object SC1 extends App {
//  private val scFactory = ActorSystem("scFactory") //ActorSystem
//  private val scActorRef = scFactory.actorOf(Props[SC1], "scActor")
//
//  override def main(args: Array[String]): Unit = {
//    //monitor the message, with var
//    var flag = true
//
//    while(flag) {
//      print("Input: ")
//      val consoleLine:String = StdIn.readLine()
//
//      // ! => send message
//      scActorRef ! consoleLine
//      if (consoleLine.equals("stop")){
//        flag = false
//        println("End")
//      }
//
//      Thread.sleep(100)
//    }
//  }
//
//}