package com.example

object Hello {

  def functions(): Unit ={
    println("________________________________________________ FUNCTIONS ________________________________________________")

    def merge[A](list1:List[A],list2:List[A], op: (A,A) => A): List[A] = {
      (list1.zip(list2)).map(x => op(x._1,x._2))
    }

    def sum(a:Int,b:Int):Int = a + b

    val l1: List[Int] = 10 to 19 toList
    val l2: List[Int] = 30 to 39 toList

    println(merge(l1,l2,sum))
    println(merge(l1,l2,(x:Int,y:Int)=> x - y))
  }

  def collectionFunctions(): Unit ={
    println("________________________________________________ COLLECTION FUNCTIONS ________________________________________________")

    //___________________________________________________________MAP
    val range = 1 to 50
    val rangeMap = range.map(math.pow(_,2))
    println(rangeMap)

    //___________________________________________________________FILTER
    val rangeFilter = range.filter(_%2==0)
    println(rangeFilter)

    //___________________________________________________________REDUCE
    val rangeReduce = range.reduce(_ + _)
    println(rangeReduce)


  }
  def collections(): Unit ={
    println("________________________________________________ COLLECTIONS ________________________________________________")

    println("________________________________________________ Immutable Lists")
    //Some operations on Immutable lists
    val l1  = List[Int]()
    val l2 =  1 :: l1
    val l3 =  2 :: l2
    val l4 =  l3 :+ 3
    val l5 = l4.drop(1)
    val l6 = l4.dropRight(1)
    val l7 = l4.patch(1,Nil,1)

    println("Empty list => " +l1)
    println("Adding an element => " +l2)
    println("Adding an element => " +l3)
    println("Adding an element on the end => " +l4)
    println("Removing the first element => " +l5)
    println("Removing the last element => " +l6)
    println("Removing the middle element => " + l7)

    println("________________________________________________ Mutable Lists")
    //Some operations on Mutable lists
    val ab = scala.collection.mutable.ArrayBuffer[Int]()
    ab += 2
    ab += 3
    4 +=: ab
    println(ab)
    ab.remove(1)
    println(ab)

    println("________________________________________________ Tuples")
    //Tuples
    var ip = ("150.165","80")
    var ip1 = ip.copy(_2 = "8080")
    println(ip)
    println(ip1)

    println("________________________________________________ HashMap")
    var map = scala.collection.mutable.HashMap[String,Int]()
    map.+=("um" -> 1)
    map.+=("dois" -> 2)
    println(map)
    var map2 = map.filterKeys(_.startsWith("u"))
    println(map)
    println(map2)
    map.-=("dois")
    println(map)
    map.+=("um" -> 1)
    println(map)



  }

  def traits(): Unit ={
    println("________________________________________________ TRAITS ________________________________________________")

    //Trait that calculate an unary operation over the result of a binary operation
    trait OperationsTrait {
      def binary(a:Double,b:Double):Double

      def unary(a:Double):Double

      def calculation(a:Double,b:Double):Double ={
        unary(binary(a,b))
      }
    }

    //Squares the sum of 2 ints
    class SumAndSqr extends OperationsTrait{
      override def binary(a: Double, b: Double): Double = a+b
      override def unary(a: Double): Double = math.pow(a,2)
    }

    var calc = new SumAndSqr()
    println(calc.calculation(1,2))
  }

  def patternMatching(): Unit ={
    println("________________________________________________ PATTERN MATCHING ________________________________________________")

    //Classes that extends Auto
    trait Auto
    class Car extends Auto()
    class Bike extends Auto()
    class MotoBike extends Auto()
    class Plain extends Auto()
    //Not a automobile
    class Chair()

    val listOfThings = List(new Car,new Bike, new MotoBike, new Plain, new Chair())

    //Verifies the automobile type and return a correspondent String. If it is not a automobile, returns 'not an auto'
    def print[A](auto: A): String = {
      auto match {
        case x: Car=> "Carro"
        case x: Bike => "Bicileta"
        case x: MotoBike => "Moto"
        case x: Auto => "another auto"
        case _ => "not an auto"
      }
    }

    println(listOfThings.map(print(_)))
  }

  def options():Unit ={
    println("________________________________________________ OPTIONS ________________________________________________")

    trait Automobile{
      val name:String
      var brand:String
    }
    //Classes that extends Automobile
    class Car (override  val name:String, override var brand:String) extends Automobile
    class Motocycle (override  val name:String, override var brand:String) extends Automobile
    class Bike (override  val name:String, override var brand:String) extends Automobile

    //Class that have a Option of an Automobile. In other words, could have an Automobile object or a None
    class Person(val auto: Option[Automobile])

    //Implicit function that transform an Automobile in Option
    implicit def auto2Option (auto:Automobile):Option[Automobile]={
      Some(auto)
    }

    //Create a List of Person, with one instance receiving None. Implicitly, the Automobile objects are converted to Option
    val people:List[Person] = List(new Person(new Car("Ferrari","Fiat")),new Person(new Motocycle("Biz","Honda")),new Person(None))

    //For each element of people list, if the person has an Automobile returns a tuple of the name and brand of the Automobile
    var peoplesAutos = people.map(_.auto match {
      case Some(a:Automobile) => (a.name,a.brand)
      case _ => ()
    }).filter(_ != ())

    println("People => " + people)
    println("People's autos => " + peoplesAutos)

  }

  def implicitMethod(): Unit ={
    println("________________________________________________ IMPLICIT METHOD ________________________________________________")
    //Create a implicit function that receive a String and return an User object, with this String as the nameUser.

    class Phonebook(){
      var users:List[User] =  List[User]()

      def addUser(user:User): Unit ={
        users = user :: users
      }
    }

    class User (var name:String){
      override def toString():String = "User > " + this.name
    }

    //implicit function that receive a String and return an User object, with this String as the nameUser.
    implicit def name2User(name:String):User = new User(name)

    val phonebook = new Phonebook

    //Implicitly the function name2User is called to transform the name to a User with this name
    phonebook.addUser("andryw")
    phonebook.addUser("outro")

    println(phonebook.users)


  }
  def implicitObject(): Unit ={
    println("________________________________________________ IMPLICIT OBJECT ________________________________________________")

    //Function receives another function implicitly
    //Apply that function to each i-th element of list1 and list2, and the result is putted on the i-th position of the returned list
    def implictMerge[A](list1:List[A],list2:List[A])(implicit op: (A,A) => A): List[A] = {
      (list1.zip(list2)).map(x => op(x._1,x._2))
    }

    val l1: List[Int] = 10 to 19 toList
    val l2: List[Int] = 30 to 39 toList

    //Implicit function that sums two ints
    implicit def sum(a:Int,b:Int):Int = a + b

    //Calls the function and pass the parameter implicitly
    println(implictMerge(l1,l2))

  }

  def partiallyAppliedFunctions(): Unit ={
    println("________________________________________________ PARTIALLY APPLIED FUNCTIONS ________________________________________________")
    //concat 3 strings
    def concat(str1:String,str2:String,str3:String):String = str1 + str2 + str3

    //put signature, calling concat
    val putSignature = concat("Sr. ",_:String,_:String)

    println(putSignature("Andryw ","Marques"))
  }

  def main(args: Array[String]): Unit = {
    functions()
    collectionFunctions()
    collections()
    traits()
    patternMatching()
    options()
    implicitMethod()
    implicitObject()
    partiallyAppliedFunctions()
  }
}
