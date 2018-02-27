package com.example.ayush.notsy

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.model.Note
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_notedetail.*

/**
 * Created by ayush on 2/21/18.
 */
class NoteDetailFragment : BaseFragment() {

    private var noteflag: Int = 0
    private var noteId: Long = -1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when (noteflag) {
            0 -> {
            }
            1 -> {
                accessStorage()
            }
            else -> {
                getNoteContents(noteId)
            }
        }
    }

    private fun getNoteContents(noteId: Long) {
        (activtiy as MainActivity).noteViewModel.getNoteDetails(noteId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    newNoteEditext.setText(it.noteText)
                }, {
                    Log.d("Notsy", it.localizedMessage)
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteflag = arguments.getInt(INTENT_TYPE)
        noteId = arguments.getLong(INTENT_NOTEID, -1)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_notedetail, container, false)

    override fun setupFragmentDaggerComponent() {

    }

    fun getNoteModel() = Note(noteId, newNoteEditext.text.toString().trim())

    companion object {

        private val INTENT_TYPE = "intent_notetype"
        private val INTENT_NOTEID = "intent_noteId"
        val CODE_READ_STORAGE = 1001
        val CODE_READ_IMAGE = 1002


        fun getInstance(type: Int, id: Long) = NoteDetailFragment().apply {

            val bundle = Bundle()
            bundle.putLong(INTENT_NOTEID, id)
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