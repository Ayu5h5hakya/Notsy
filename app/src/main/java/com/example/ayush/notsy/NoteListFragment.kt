package com.example.ayush.notsy

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ayush.notsy.adapter.NotesAdapter
import com.example.ayush.notsy.dagger.module.NoteModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
        (context as MainActivity).compositeDisposable.add(subscribeToTextChanges())
        (context as MainActivity).compositeDisposable.add(subscribeToNotes())
        (context as MainActivity).compositeDisposable.add(subscribeToNoteDelete())
    }

    private fun subscribeToTextChanges(): Disposable {
        return (context as MainActivity).noteViewModel.createNoteStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    notesAdapter.addNote(it)
                }, {
                    Log.d("Notsy", it.localizedMessage)
                })
    }

    private fun subscribeToNoteDelete() = (context as MainActivity).noteViewModel.createNoteDeleteStream().
            observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                (context as MainActivity).noteViewModel.updateRecycler()
            }, {
                Log.d("Notsy", it.localizedMessage)
            })

    private fun subscribeToNotes(): Disposable {
        return (context as MainActivity).noteViewModel.createAllNotesStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    notesAdapter.addAll(it)
                }, {
                    Log.d("Notsy", it.localizedMessage)
                })
    }

    fun onTop(){
        (context as MainActivity).noteViewModel.updateRecycler()
    }


    companion object {

        fun getInstance() = NoteListFragment().apply {  }

    }


}