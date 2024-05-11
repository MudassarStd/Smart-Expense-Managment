package com.example.mobileappproject.classes
import com.example.mobileappproject.classes.ClassA
import kotlinx.coroutines.processNextEventInCurrentThread


interface listPasserInterface{
    fun passList(list : MutableList<String>) : MutableList<String>
}




fun main()
{

    val object_A = ClassA()


    // objectInterface is initialized with object of class that implements it.
    val interfaceObject : listPasserInterface = object_A

    val dataList : MutableList<String> = mutableListOf("list item 1","list item 2","list item 3","list item 4")
    interfaceObject.passList(dataList)
    println(dataList)
}

class ClassB {
}