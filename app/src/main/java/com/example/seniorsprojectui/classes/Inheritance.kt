package com.example.seniorsprojectui.classes


fun main()
{
    // on object initialization, parent constructor is always called first
    val obj = Child()

    obj.familyRituals()
    obj.familyName
}

open class Parent {

    val name : String = ""
    val age : Int = -1
    open val familyName : String = "Parent"

    open fun familyRituals()
    {
        println("Family Rituals in Parents")
    }

}

class Child : Parent() {

    override val familyName: String = "Child"

    override fun familyRituals() {
        println("Family Rituals in Child")
        println("FamilyName in Child: $familyName")
        super.familyRituals()
    }

}