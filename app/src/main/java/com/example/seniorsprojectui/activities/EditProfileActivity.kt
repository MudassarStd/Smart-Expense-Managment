package com.example.seniorsprojectui.activities

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.CurrentUserSession
import com.example.seniorsprojectui.backend.UserData
import com.example.seniorsprojectui.databinding.ActivityEditProfileBinding
import com.example.seniorsprojectui.dbvm.ViewModelUsers
import com.example.seniorsprojectui.fragments.AddAttachmentBSV
import com.example.seniorsprojectui.fragments.EditProfileImageBSVFragment
import java.io.InputStream

class EditProfileActivity : AppCompatActivity(), AddAttachmentBSV.OnAttachmentSelected {
    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var viewModelUsers: ViewModelUsers
    private lateinit var userData : UserData
    private lateinit var addAttachmentBSV: AddAttachmentBSV
    private  var userImage : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addAttachmentBSV = AddAttachmentBSV(false)
        addAttachmentBSV.invokeOnAttachmentSelectedInterface(this)
        viewModelUsers = ViewModelProvider(this)[ViewModelUsers::class.java]

        userData = CurrentUserSession.currentUserData!!
        populateUserData(userData)

        binding.btnConfirmChangesToProfile.setOnClickListener {
            if(checkUpdates(userData)) {
                viewModelUsers.updateUser(userData)
                populateUserData(userData)
                showDoneDialog()
            } else {
                Toast.makeText(this, "No changes detected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivEditProfileImage.setOnClickListener {
            addAttachmentBSV.show(supportFragmentManager, addAttachmentBSV.tag)
        }
    }

    private fun populateUserData(user: UserData) {
        binding.etUserEmailEditProfile.setText(user.useremail)
        binding.etUserNameEditProfile.setText(user.username)
        binding.etUserPasswordEditProfile.setText(user.userpassword)
        loadImageFromUri(user.userImage.toUri())
    }

    private fun checkUpdates(user: UserData): Boolean {
        var updateStatus = false

        val newUsername = binding.etUserNameEditProfile.text.toString()
        val newUserpass = binding.etUserPasswordEditProfile.text.toString()

        if (userImage != null && user.userImage != userImage.toString()) {
            user.userImage = userImage.toString()
            updateStatus = true
        }

        if (user.username != newUsername) {
            user.username = newUsername
            updateStatus = true
        }

        if (user.userpassword != newUserpass) {
            user.userpassword = newUserpass
            updateStatus = true
        }

        return updateStatus
    }

    private fun loadImageFromUri(uriAttachment: Uri) {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uriAttachment)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.ivUserImage.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.e("EditProfileActivity", "Error loading image", e)
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttachmentSelected(itemUri: String, attachmentType: String) {
        userImage = itemUri.toUri()
        loadImageFromUri(userImage!!)
    }

    override fun onAttachmentSelected(itemUri: String, attachmentType: String, docName: String) {
        // Not implemented
    }

    override fun onDeleteSignal(flag: Boolean) {
        if (flag) {
            binding.ivUserImage.setImageResource(R.drawable.ic_person)
            userImage = null
        }
    }

    private fun showDoneDialog() {
        val dialogView = layoutInflater.inflate(R.layout.done_dialog, null)
        val dialog = AlertDialog.Builder(this).setTitle("Profile Update")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ -> }
            .create()
        dialog.show()
    }
}
