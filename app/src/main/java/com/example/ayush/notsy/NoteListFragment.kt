package com.example.ayush.notsy

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ayush.notsy.adapter.NotesAdapter
import com.example.domain.model.Note
import kotlinx.android.synthetic.main.fragment_noteslist.*

/**
 * Created by ayush on 2/21/18.
 */
class NoteListFragment : BaseFragment() {

    private lateinit var notesAdapter : NotesAdapter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notesList.layoutManager = LinearLayoutManager(context)
        notesAdapter = NotesAdapter(activtiy)
        notesList.adapter = notesAdapter

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_noteslist, container, false)

    override fun setupFragmentDaggerComponent() {
    }

    override fun onResume() {
        super.onResume()
        onTop()
    }

    fun onTop(){
        (context as MainActivity).noteViewModel.updateRecycler()
    }


    companion object {

        fun getInstance() = NoteListFragment().apply {  }

    }

    fun addNote(it: Note) {
        notesAdapter.addNote(it)
    }

    fun addAll(it: MutableList<Note>) {
        notesAdapter.addAll(it)
    }


}