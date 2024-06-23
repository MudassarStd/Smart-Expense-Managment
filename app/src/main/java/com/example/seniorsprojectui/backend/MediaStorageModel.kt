package com.example.seniorsprojectui.backend

import android.net.Uri
import android.provider.MediaStore
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MediaStorageModel {

    companion object{
        fun loadImageFromUri(uriAttachment: Uri, context : Context) : Bitmap? {
                return try {
                    val inputStream: InputStream? = context.contentResolver.openInputStream(uriAttachment)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    bitmap
                } catch (e: Exception) {
                    Log.e("EditTransactionActivity", "Error loading image", e)
                    Toast.makeText(context, "Error loading image", Toast.LENGTH_SHORT).show()
                    null
                }
            }



            fun getFileName(uri: Uri, activity: Activity): String {
            var result: String? = null
            if (uri.scheme == "content") {
                activity.contentResolver.query(uri, null, null, null, null)?.apply {
                    if (moveToFirst()) {
                        val displayNameIndex: Int
                        val mimeType = activity.contentResolver.getType(uri)
                        displayNameIndex = if (mimeType != null && mimeType.startsWith("image/")) {
                            getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)
                        } else {
                            getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                        }
                        result = if (displayNameIndex != -1) {
                            getString(displayNameIndex)
                        } else {
                            null
                        }
                        close()
                    }
                }
            }
            if (result == null) {
                result = uri.path
                val cut = result?.lastIndexOf('/')
                if (cut != null && cut != -1) {
                    result = result?.substring(cut + 1)
                }
            }
            return result ?: "Unknown"
        }


        fun saveBitmapAndGetUri(bitmap: Bitmap, context: Context): Uri {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            val imageFile = File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            )
            try {
                FileOutputStream(imageFile).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    Log.d("AddAttachmentBSV", "Bitmap saved" )
                }
            } catch (e: IOException) {
                Log.e("AddAttachmentBSV", "Error saving bitmap", e)
            }
            return FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )
        }

    }
}