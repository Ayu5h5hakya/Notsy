package com.example.ayush.notsy.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.ayush.notsy.R
import com.example.domain.model.Note
import inflate
import kotlinx.android.synthetic.main.child_note.view.*

/**
 * Created by ayush on 2/16/18.
 */
class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notes : MutableList<Note> by lazy { mutableListOf<Note>() }

    override fun getItemCount() = notes.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = NotesViewHolder(parent)

    override fun onBindViewHolder(holder: NotesViewHolder?, position: Int) {
        holder?.bind(notes[position])
    }

    fun addAll(notesList : List<Note>){
        notes.clear()
        notes.addAll(notesList)
        notifyDataSetChanged()
    }

    fun addNote(note : Note){
        notes.add(note)
    }

    fun deleteAll(){
        notes.clear()
    }

    fun delete(noteId : Long){
        notes.filter { it.id == noteId }
    }

    class NotesViewHolder(parent: ViewGroup?) : RecyclerView.ViewHolder(parent?.inflate(R.layout.child_note)){

        fun bind(note : Note) = itemView.apply {
            childNoteTextView.text = note.noteText
        }

    }

}