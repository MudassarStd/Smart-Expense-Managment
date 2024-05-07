package com.example.seniorsprojectui.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.seniorsprojectui.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddAttachmentBSV : BottomSheetDialogFragment() {

    companion object {
        private const val REQUEST_IMAGE_GALLERY = 2
        private const val REQUEST_PICK_DOCUMENT = 3
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


        camera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(takePictureIntent)
            } else {
                // Handle the case where the camera app is not installed
            }

        }

        imageGallery.setOnClickListener {
            val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                startActivityForResult(Intent.createChooser(pickDocumentIntent, "Select Document"), REQUEST_PICK_DOCUMENT)
            } else {
                // Handle the case where the document picker app is not installed
            }


        }

    }



}