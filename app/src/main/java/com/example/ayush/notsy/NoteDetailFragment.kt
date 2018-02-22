package com.example.ayush.notsy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_notedetail.*

/**
 * Created by ayush on 2/21/18.
 */
class NoteDetailFragment : BaseFragment() {

    private var noteflag : Int = 0
    private var noteId : Long = -1

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when(noteflag){
            0 ->{}
            1 ->{}
            else ->{
                getNoteContents(noteId)
            }
        }
    }

    private fun getNoteContents(noteId: Long) {
        (activtiy as MainActivity).noteViewModel.getNoteDetails(noteId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    newNoteEditext.setText(it.noteText)
                },{
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

    fun getNoteModel() = newNoteEditext.text.toString().trim()

    companion object {

        private val INTENT_TYPE = "intent_notetype"
        private val INTENT_NOTEID = "intent_noteId"


        fun getInstance(type : Int, id : Long) = NoteDetailFragment().apply {
            val bundle = Bundle()
            bundle.putLong(INTENT_NOTEID, id)
            bundle.putInt(INTENT_TYPE, type)
            arguments = bundle
        }

    }


}