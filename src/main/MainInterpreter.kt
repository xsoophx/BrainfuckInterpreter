package main

fun main(){
    val currentBaseClass = MainInterpreterBase()

    currentBaseClass.`print final interpreted output`()
}

class MainInterpreterBase{

    fun `print final interpreted output`(){
        println("initial commit")
    }
}
