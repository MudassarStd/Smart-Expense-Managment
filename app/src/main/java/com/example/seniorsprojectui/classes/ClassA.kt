package com.example.mobileappproject.classes


fun main()
{
    val obj_A = ClassA()
    val p1 = Person("this")
    println(p1.name)

    // for below 2 code statements, setter is called
    p1.name = ""
    p1.name = "Mudassar"

}




class ClassA() : listPasserInterface {
    override fun passList(list: MutableList<String>) : MutableList<String> {
        list.add("100")
        return list
    }
}
