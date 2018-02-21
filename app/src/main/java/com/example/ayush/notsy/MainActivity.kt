package com.example.ayush.notsy

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.ayush.notsy.adapter.NotesAdapter
import com.example.ayush.notsy.dagger.module.NoteModule
import com.example.data.NoteViewModel
import kotlinx.android.synthetic.main.activity_main_collapsed.*
import javax.inject.Inject

class MainActivity : BaseActivity(), View.OnClickListener {

    @Inject lateinit var noteViewModel: NoteViewModel
    @Inject lateinit var notesAdapter: NotesAdapter
    private val srcConstraint: ConstraintSet by lazy { ConstraintSet() }
    private val desConstraint: ConstraintSet by lazy { ConstraintSet() }
    private var startAnimation = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_collapsed)
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
                .plus(NoteModule())
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

        if (supportFragmentManager.fragments.size != 0 && supportFragmentManager.fragments[0] is NoteListFragment) initNoteDetail(type)
        else (supportFragmentManager.fragments[1] as NoteDetailFragment).addNoteType(type)

    }

    private fun initNotesList() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, NoteListFragment.getInstance()).commit()
    }

    private fun initNoteDetail(type: Int = 0) {
        //0 = text
        //1 = image
        //2 = audio
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, NoteDetailFragment.getInstance(type)).addToBackStack(null).commit()
    }

    private fun initChildButtons() {
        startAnimation = false
        srcConstraint.clone(setLayout)
        desConstraint.clone(this, R.layout.activity_main_expanded)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 1 && supportFragmentManager.fragments[1] is NoteDetailFragment) {
            noteViewModel.saveNote((supportFragmentManager.fragments[1] as NoteDetailFragment).getNoteModel())
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
            noteViewModel.deleteNotes()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
