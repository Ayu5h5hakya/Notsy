package com.example.ayush.notsy

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.ayush.notsy.adapter.NotesAdapter
import com.example.ayush.notsy.dagger.module.NoteModule
import com.example.data.NoteViewModel
import kotlinx.android.synthetic.main.activity_main_collapsed.*
import kotlinx.android.synthetic.main.activity_main_home.*
import javax.inject.Inject

class MainActivity : BaseActivity(), View.OnClickListener, NotesAdapter.OnNoteClickListener {

    @Inject lateinit var noteViewModel: NoteViewModel
    @Inject lateinit var notesAdapter: NotesAdapter
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
    }

    override fun onResume() {
        super.onResume()
    }


    override fun initUiComponents() {
    }

    override fun setupActivityComponent() {
        NotsyApplication.get(this)
                .getAppComponent()
                .plus(NoteModule(this))
                .inject(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.addNoteButton -> {
                TransitionManager.beginDelayedTransition(setLayout)
                val constraint = if (startAnimation) srcConstraint else desConstraint
                constraint.applyTo(setLayout)
                startAnimation = !startAnimation
            }
            R.id.addNoteTextButton -> {
                if (supportFragmentManager.fragments.size != 0 && supportFragmentManager.fragments[0] is NoteListFragment) {
                    initNoteDetail(0)
                } else {

                }
            }
            R.id.addNoteImageButton -> {
                if (supportFragmentManager.fragments.size != 0 && supportFragmentManager.fragments[0] is NoteListFragment) {
                    initNoteDetail(1)
                } else {

                }
            }
        }
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

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 1 && supportFragmentManager.fragments[1] is NoteDetailFragment) {
            noteViewModel.saveNote((supportFragmentManager.fragments[1] as NoteDetailFragment).getNoteModel().noteText)
            supportFragmentManager.popBackStackImmediate()
            (supportFragmentManager.fragments[0] as NoteListFragment).onTop()
        } else super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.delete_notes -> {
            if (supportFragmentManager.fragments.size > 1 && supportFragmentManager.fragments[1] is NoteDetailFragment) {
                val notetodelete = (supportFragmentManager.fragments[1] as NoteDetailFragment).getNoteModel().id
                if (notetodelete != null) noteViewModel.deleteNotes(notetodelete)

            } else noteViewModel.deleteNotes()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
