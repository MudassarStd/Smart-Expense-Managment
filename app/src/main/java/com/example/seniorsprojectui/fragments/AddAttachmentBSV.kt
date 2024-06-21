package com.example.seniorsprojectui.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.MediaStorageModel
import com.example.seniorsprojectui.backend.MediaStorageModel.Companion.saveBitmapAndGetUri
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddAttachmentBSV : BottomSheetDialogFragment() {
    // interface
    interface OnAttachmentSelected{
        fun onAttachmentSelected(itemUri : String, attachmentType : String)
    }

    private var listener : OnAttachmentSelected?  = null

    fun invokeOnAttachmentSelectedInterface(listener : OnAttachmentSelected)
    {
        this.listener = listener
    }

    private lateinit var tvDocument : TextView
    private lateinit var ivDocument : ImageView
    private lateinit var camImage : Uri
    private lateinit var galleryImage : Uri
    private lateinit var document : Uri

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
                        Log.d("AddAttachmentBSV", "save Method called" )
                        // sending ImageUri back to activity
                        listener?.onAttachmentSelected(selectedImageUri.toString(), "img")
                    }
                }
                REQUEST_PICK_DOCUMENT -> {
                    data?.data?.let { documentUri ->
                        val documentName = MediaStorageModel.getFileName(documentUri, requireActivity())
                        tvDocument.text = documentName
                        Log.d("AddAttachmentBSV", "save Method called" )
                        // getting uri
                        listener?.onAttachmentSelected(documentUri.toString(), "doc")
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    Log.d("AddAttachmentBSV", "save Method called" )
                    var selectedImageUri = saveBitmapAndGetUri(imageBitmap,requireContext())
//                    ivDocument.setImageURI(selectedImageUri)
                    ivDocument.setImageURI(selectedImageUri)
                    listener?.onAttachmentSelected(selectedImageUri.toString(), "img")
                    // getting camImage uri

                }
            }
        }
    }


}