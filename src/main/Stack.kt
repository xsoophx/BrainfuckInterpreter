package main

class Stack<T>() {

    private var stackList = setOf<T>()

    constructor (command: List<T>) : this() {
        add(command)
    }

    fun popStack(): T {
        return stackList.last()
    }

    fun pushStack(newElement: T ){
        stackList += newElement }


    fun add (command: List<T>){
        command.forEach {
                c: T -> pushStack(c) }
    }

}

