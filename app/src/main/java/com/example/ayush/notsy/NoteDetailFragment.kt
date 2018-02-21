package com.example.ayush.notsy

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_notedetail.*

/**
 * Created by ayush on 2/21/18.
 */
class NoteDetailFragment : BaseFragment() {

    private var noteflag: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (noteflag == 1) {
            accessStorage()
        } else {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteflag = arguments.getInt(INTENT_TYPE)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_notedetail, container, false)

    override fun setupFragmentDaggerComponent() {

    }

    fun getNoteModel() = newNoteEditext.text.toString().trim()

    companion object {

        private val INTENT_TYPE = "intent_notetype"
        private val CODE_READ_STORAGE = 1001
        private val CODE_READ_IMAGE = 1002


        fun getInstance(type: Int) = NoteDetailFragment().apply {
            val bundle = Bundle()
            bundle.putInt(INTENT_TYPE, type)
            arguments = bundle
        }

    }

    private fun accessStorage() {
        if (!activtiy.hasPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)))
            activtiy.askForPermissions((arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)), CODE_READ_STORAGE)
        else readGallery()
    }

    private fun readGallery() {
        val fileintent = Intent(Intent.ACTION_GET_CONTENT)
        val uri = Uri.parse(Environment.getExternalStorageDirectory().path + "/Pictures")
        fileintent.setDataAndType(uri, "image/*")
        startActivityForResult(Intent.createChooser(fileintent, "Choose a picture"), CODE_READ_IMAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            CODE_READ_STORAGE ->
                if (activtiy.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) readGallery()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CODE_READ_IMAGE) {

            when (resultCode) {

                Activity.RESULT_OK -> {
                    data?.data.let {
                        noteImageView.visibility = View.VISIBLE
                        Picasso.with(activtiy).load(it).into(noteImageView)
                    }
                }

            }
        }

    }

    fun addNoteType(type: Int) {
        when (type) {
            0 -> {
            }
            1 -> accessStorage()
            2 -> {
            }
        }
    }


}