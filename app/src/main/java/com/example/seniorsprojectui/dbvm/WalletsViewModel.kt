package com.example.seniorsprojectui.dbvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.seniorsprojectui.maindb.TestDB

class WalletsViewModel(application: Application) : AndroidViewModel(application) {

    private val database : TestDB = TestDB.getInstance(application)


}