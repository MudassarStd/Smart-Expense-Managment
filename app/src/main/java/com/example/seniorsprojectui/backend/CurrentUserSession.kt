package com.example.seniorsprojectui.backend

class CurrentUserSession {
    companion object
    {
        var currentUserId = 0
        var currentUserName = "Null"
        var currentUserData : UserData? = null


        val notifiedCategories: MutableSet<String> = mutableSetOf()
    }
}