package com.example.ayush.notsy

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.ayush.notsy.adapter.NotesAdapter
import com.example.ayush.notsy.dagger.module.NoteModule
import com.example.data.NoteViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.editorlinearlayout.*
import javax.inject.Inject

class MainActivity : BaseActivity(), View.OnClickListener {

    @Inject lateinit var noteViewModel: NoteViewModel
    @Inject lateinit var notesAdapter : NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addNoteButton.setOnClickListener(this)
        notesRecycleView.layoutManager = LinearLayoutManager(this)
        notesRecycleView.adapter = notesAdapter
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(subscribeToTextChanges())
        compositeDisposable.add(subscribeToNotes())
    }

    private fun subscribeToTextChanges() : Disposable{
        return noteViewModel.createNoteStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    noteViewModel.updateRecycler()
                },{
                    Log.d("Notsy", it.localizedMessage)
                })
    }

    private fun subscribeToNotes() : Disposable{
        return noteViewModel.createAllNotesStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    notesAdapter.addAll(it)
                },{
                    Log.d("Notsy", it.localizedMessage)
                })
    }


    override fun initUiComponents() {
    }

    override fun setupActivityComponent() {
        NotsyApplication.get(this)
                .getAppComponent()
                .plus(NoteModule())
                .inject(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.addNoteButton -> noteViewModel.saveNote(newNoteEditText.text.toString().trim())
        }
    }

}
