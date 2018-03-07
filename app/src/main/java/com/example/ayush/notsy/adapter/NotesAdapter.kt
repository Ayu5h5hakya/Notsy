package com.example.ayush.notsy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.ayush.notsy.R
import com.example.domain.ListElement
import com.example.domain.model.Note
import com.squareup.picasso.Picasso
import inflate
import kotlinx.android.synthetic.main.child_note.view.*
import kotlinx.android.synthetic.main.child_note_with_image.view.*

/**
 * Created by ayush on 2/16/18.
 */
class NotesAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val notes: MutableList<ListElement> by lazy { mutableListOf<ListElement>() }
    val onNoteClickListener: OnNoteClickListener

    init {
        onNoteClickListener = context as OnNoteClickListener
    }

    override fun getItemCount() = notes.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        ListElement.ITEM_NOTEWIMAGE -> {
            NotesImageViewHolder(parent)
        }
        else -> NotesViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (getItemViewType(position)) {
            ListElement.ITEM_NOTEWIMAGE -> {
                (holder as NotesImageViewHolder).bind(notes[position])
            }
            else -> (holder as NotesViewHolder).bind(notes[position])
        }
    }

    override fun getItemViewType(position: Int) = notes[position].getType()

    fun addAll(notesList: List<Note>) {
        notes.clear()
        notes.addAll(notesList)
        notifyDataSetChanged()
    }

    fun addNote(note: Note) {
        val updateIndex = notes.indexOfFirst {
            it as Note
            it.id == note.id
        }
        if (updateIndex != -1) notes.removeAt(updateIndex)
        notes.add(note)
        notifyItemInserted(notes.size)

    }

    fun deleteAll() {
        notes.clear()
    }

    inner class NotesViewHolder(parent: ViewGroup?) : RecyclerView.ViewHolder(parent?.inflate(R.layout.child_note)), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(note: ListElement) = itemView.apply {
            note as Note
            childNoteTextView.text = note.textContent
            val date = note.dateStamp.split(" ")
//            if(adapterPosition == 0 || ((notes[adapterPosition - 1] as Note).dateStamp != note.dateStamp)) dateTextView.text = date[0] + "\n" + date[1]
//            childDateTextView.text = note.timeStamp
        }

        override fun onClick(view: View) {

            onNoteClickListener.onNoteClicked((notes[adapterPosition] as Note).id)
        }

    }

    inner class NotesImageViewHolder(parent: ViewGroup?) : RecyclerView.ViewHolder(parent?.inflate(R.layout.child_note_with_image)), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(note: ListElement) = itemView.apply {
            note as Note
            noteText.text = note.textContent
            Picasso.with(context).load(note.imageContent).into(noteImag)
            val date = note.dateStamp.split(" ")
            if(adapterPosition == 0 || ((notes[adapterPosition - 1] as Note).dateStamp != note.dateStamp)) dateTextViewImg.text = date[0] + "\n" + date[1]
        }

        override fun onClick(view: View) {

            onNoteClickListener.onNoteClicked((notes[adapterPosition] as Note).id)
        }

    }

    interface OnNoteClickListener {

        fun onNoteClicked(noteId: Long?)

    }

}