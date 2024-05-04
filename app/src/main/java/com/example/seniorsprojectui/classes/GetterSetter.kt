package com.example.mobileappproject.classes


fun main()
{
    val p1 = Person("this")
    println(p1.name)

    // setting value to Person's name property
    // for below 2 code statements, setter is called
    p1.name = ""
    p1.name = "Mudassar"

    // getting value of name
    println(p1.name)

}

// getter and setter are implemented below properties of their interest

class Person(nameParam : String){

    // setter for name property is defined below it.
    // set() is only called when we set value of name using object of Person.
    // field in getter/setter means our property in class (ex. name)

    var name  = nameParam
        set(value) {
            println("Setter is called")
            // setter will only assign our value to name property if its not empty, thus validating data
            if (value.isNotEmpty())
            {
                field = value
            }
            else{
                println("Name should not be Empty")
            }
        }
        // it is called when we try to get/print value of name using obj of Person Class.
        get() {
            println("getter is called")
            return field.toUpperCase()
        }

}
