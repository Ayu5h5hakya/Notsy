package com.example.ayush.notsy

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.ayush.notsy.adapter.NotesAdapter
import com.example.ayush.notsy.dagger.module.NoteModule
import com.example.data.NoteViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main_collapsed.*
import kotlinx.android.synthetic.main.activity_main_home.*
import javax.inject.Inject

class MainActivity : BaseActivity(), View.OnClickListener, NotesAdapter.OnNoteClickListener {

    @Inject lateinit var noteViewModel: NoteViewModel
    private val srcConstraint: ConstraintSet by lazy { ConstraintSet() }
    private val desConstraint: ConstraintSet by lazy { ConstraintSet() }
    private var startAnimation = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)
        setSupportActionBar(notsyToolbar as Toolbar)
        initNotesList()
        initChildButtons()
        addNoteButton.setOnClickListener(this)
        addNoteTextButton.setOnClickListener(this)
        addNoteImageButton.setOnClickListener(this)

        compositeDisposable.add(subscribeToTextChanges())
        compositeDisposable.add(subscribeToNotes())

    }

    override fun onResume() {
        super.onResume()
    }

    private fun subscribeToTextChanges(): Disposable {
        return noteViewModel.createNoteStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.textContent.equals("Error"))
                    (supportFragmentManager.fragments[0] as NoteListFragment).addNote(it)
                    else{
                        Log.d("Notsy", "Error caught")
                    }
                }, {
                    Log.d("Notsy", it.localizedMessage)
                },{
                    Log.d("Notsy", "complete")
                })
    }

    private fun subscribeToNoteDelete() = noteViewModel.createNoteDeleteStream().
            observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (supportFragmentManager.fragments.size > 1 && supportFragmentManager.fragments[1] is NoteDetailFragment) gobackToList()
                else noteViewModel.updateRecycler()
            }, {
                Log.d("Notsy", it.localizedMessage)
            })

    private fun subscribeToNotes(): Disposable {
        return noteViewModel.createAllNotesStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (supportFragmentManager.fragments[0] as NoteListFragment).addAll(it)
                }, {
                    Log.d("Notsy", it.localizedMessage)
                })
    }

    override fun initUiComponents() {
    }

    override fun setupActivityComponent() {
        NotsyApplication.get(this)
                .getAppComponent()
                .plus(NoteModule(this))
                .inject(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.addNoteButton -> {
                TransitionManager.beginDelayedTransition(setLayout)
                val constraint = if (startAnimation) srcConstraint else desConstraint
                constraint.applyTo(setLayout)
                startAnimation = !startAnimation
            }
            R.id.addNoteTextButton -> addNewNoteType(0)
            R.id.addNoteImageButton -> addNewNoteType(1)
        }
    }

    private fun addNewNoteType(type: Int) {
        addNoteButton.performClick()
        if (supportFragmentManager.fragments.size == 1 && supportFragmentManager.fragments[0] is NoteListFragment) initNoteDetail(type)
        else (supportFragmentManager.fragments[1] as NoteDetailFragment).addNoteType(type)

    }

    private fun initNotesList() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, NoteListFragment.getInstance()).commit()
    }

    fun initNoteDetail(type: Int = -1, id: Long? = -1L) {
        //0 = text
        //1 = image
        //2 = audio
        if (id != null)
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, NoteDetailFragment.getInstance(type, id)).addToBackStack(null).commit()
    }

    private fun initChildButtons() {
        startAnimation = false
        srcConstraint.clone(setLayout)
        desConstraint.clone(this, R.layout.activity_main_expanded)
    }

    override fun onNoteClicked(noteId: Long?) {
        initNoteDetail(id = noteId)
    }

    private fun gobackToList() {
        supportFragmentManager.popBackStackImmediate()
        (supportFragmentManager.fragments[0] as NoteListFragment).onTop()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 1 && supportFragmentManager.fragments[1] is NoteDetailFragment) {
            noteViewModel.saveNote((supportFragmentManager.fragments[1] as NoteDetailFragment).getNoteModel())
            gobackToList()
        } else super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun setAlarmVisibility(visbility : Boolean){
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.delete_notes -> {
            compositeDisposable.add(subscribeToNoteDelete())
            if (supportFragmentManager.fragments.size > 1 && supportFragmentManager.fragments[1] is NoteDetailFragment) {
                val notetodelete = (supportFragmentManager.fragments[1] as NoteDetailFragment).getNoteModel().id
                if (notetodelete != null) noteViewModel.deleteNotes(notetodelete)

            } else noteViewModel.deleteNotes()
            true
        }
        R.id.set_alarm_note -> {
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
