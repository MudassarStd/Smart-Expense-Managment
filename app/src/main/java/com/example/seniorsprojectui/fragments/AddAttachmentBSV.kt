package com.example.seniorsprojectui.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.seniorsprojectui.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddAttachmentBSV : BottomSheetDialogFragment() {


    private lateinit var tvDocument : TextView
    private lateinit var ivDocument : ImageView
    companion object {
        private const val REQUEST_IMAGE_GALLERY = 2
        private const val REQUEST_PICK_DOCUMENT = 3
        private const val REQUEST_IMAGE_CAPTURE = 4
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_attachment_b_s_v, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val camera = view.findViewById<CardView>(R.id.cvAttactmentCamera)
        val imageGallery = view.findViewById<CardView>(R.id.cvAttactmentImage)
        val document = view.findViewById<CardView>(R.id.cvAttactmentDocument)

        tvDocument = view.findViewById(R.id.tvShowSelectedDocument)
        ivDocument = view.findViewById(R.id.ivShowImageSelected)
//        tvDocument.visibility = View.VISIBLE


        camera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(takePictureIntent)
            } else {
                // Handle the case where the camera app is not installed
            }

        }

        imageGallery.setOnClickListener {
            val pickPhotoIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            if (pickPhotoIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_GALLERY)
            } else {
                // Handle the case where the image gallery app is not installed
            }

        }

        document.setOnClickListener {
            val pickDocumentIntent = Intent(Intent.ACTION_GET_CONTENT)
            pickDocumentIntent.type = "application/*" // Set the MIME type here according to your requirement

            if (pickDocumentIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(
                    Intent.createChooser(pickDocumentIntent, "Select Document"),
                    REQUEST_PICK_DOCUMENT
                )
            } else {
                // Handle the case where the document picker app is not installed
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_GALLERY -> {
                    data?.data?.let { imageUri ->
                        var selectedImageUri = imageUri
                        ivDocument.setImageURI(selectedImageUri)
                    }
                }
                REQUEST_PICK_DOCUMENT -> {
                    data?.data?.let { documentUri ->
                        val documentName = getFileName(documentUri)
                        tvDocument.text = documentName
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    var selectedImageUri = imageBitmap
//                    ivDocument.setImageURI(selectedImageUri)
                    ivDocument.setImageBitmap(imageBitmap)
                }
            }
        }
    }

    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            requireActivity().contentResolver.query(uri, null, null, null, null)?.apply {
                if (moveToFirst()) {
                    val displayNameIndex: Int
                    val mimeType = requireActivity().contentResolver.getType(uri)
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


}