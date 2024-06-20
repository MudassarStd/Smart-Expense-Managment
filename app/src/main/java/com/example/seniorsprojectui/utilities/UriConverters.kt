package com.example.seniorsprojectui.utilities

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.TypeConverter

class UriConverters {

    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    // from string to uri
    // on data request
    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }
}