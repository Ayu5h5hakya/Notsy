package com.example.ayush.notsy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notedetail.*

/**
 * Created by ayush on 2/21/18.
 */
class NoteDetailFragment : BaseFragment() {

    private var noteflag : Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (noteflag == 1){}
        else{

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


        fun getInstance(type : Int) = NoteDetailFragment().apply {
            val bundle = Bundle()
            bundle.putInt(INTENT_TYPE, type)
            arguments = bundle
        }

    }


}