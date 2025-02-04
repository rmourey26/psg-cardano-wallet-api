package iog.psg.cardano.experimental.cli

import scala.sys.process._

trait ProcessResult[A] {
  def apply(process: ProcessBuilder): A
  def map[B](f: A => B): ProcessResult[B] = a => f(apply(a))
}

object ProcessResult {
  def apply[T](implicit PR: ProcessResult[T]): ProcessResult[T] = PR

  implicit val LazyListOfStrings: ProcessResult[LazyList[String]] = _.lazyLines
  implicit val ListOfStrings: ProcessResult[List[String]] = ProcessResult[LazyList[String]].map(_.toList)
  implicit val String: ProcessResult[String] = ProcessResult[LazyList[String]].map(_.head)
  implicit val Int: ProcessResult[Int] = _.!
  implicit val Unit: ProcessResult[Unit] = _.!!
}