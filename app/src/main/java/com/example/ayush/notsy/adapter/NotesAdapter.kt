package com.example.ayush.notsy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.ayush.notsy.NoteListFragment
import com.example.ayush.notsy.R
import com.example.domain.model.Note
import inflate
import kotlinx.android.synthetic.main.child_note.view.*

/**
 * Created by ayush on 2/16/18.
 */
class NotesAdapter(context: Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notes: MutableList<Note> by lazy { mutableListOf<Note>() }
    val onNoteClickListener: OnNoteClickListener

    init {
        onNoteClickListener = context as OnNoteClickListener
    }

    override fun getItemCount() = notes.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = NotesViewHolder(parent)

    override fun onBindViewHolder(holder: NotesViewHolder?, position: Int) {
        holder?.bind(notes[position])
    }

    fun addAll(notesList: List<Note>) {
        notes.clear()
        notes.addAll(notesList)
        notifyDataSetChanged()
    }

    fun addNote(note: Note) {
        notes.add(note)
        notifyItemInserted(notes.size)

    }

    fun deleteAll() {
        notes.clear()
    }

    fun delete(noteId: Long) {
        notes.filter { it.id == noteId }
    }

    inner class NotesViewHolder(parent: ViewGroup?) : RecyclerView.ViewHolder(parent?.inflate(R.layout.child_note)), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(note: Note) = itemView.apply {
            childNoteTextView.text = note.noteText
        }

        override fun onClick(view: View) {
            onNoteClickListener.onNoteClicked(notes[adapterPosition].id)
        }

    }

    interface OnNoteClickListener {

        fun onNoteClicked(noteId: Long?)

    }

}