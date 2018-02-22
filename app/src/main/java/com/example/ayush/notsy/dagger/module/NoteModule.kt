package com.example.ayush.notsy.dagger.module

import android.content.Context
import com.example.ayush.notsy.adapter.NotesAdapter
import com.example.ayush.notsy.dagger.scope.NoteScope
import com.example.data.NoteRepositoryImpl
import com.example.data.NoteViewModel
import com.example.data.NotsyDatabase
import com.example.data.dataModel.NoteDao
import com.example.domain.repository.NoteRepository
import com.example.domain.usecase.AddNoteCase
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.GetAllNotesCase
import com.example.domain.usecase.GetNoteDetailCase
import dagger.Module
import dagger.Provides

/**
 * Created by ayush on 2/14/18.
 */
@Module
@NoteScope
class NoteModule(context: Context) {

    private val fragment = context

    @Provides
    @NoteScope
    fun provideNoteDao(notsyDatabase: NotsyDatabase) = notsyDatabase.getNoteDao()

    @Provides
    @NoteScope
    fun provideNoteViewModel(addNoteCase: AddNoteCase,
                             getAllNotesCase: GetAllNotesCase,
                             deleteNoteUseCase: DeleteNoteUseCase,
                             noteDetailCase: GetNoteDetailCase) = NoteViewModel(addNoteCase, getAllNotesCase, deleteNoteUseCase, noteDetailCase)

    @Provides
    @NoteScope
    fun provideAddNoteUseCase(noteRepository: NoteRepository) = AddNoteCase(noteRepository)

    @Provides
    @NoteScope
    fun provideGetNotesUseCase(noteRepository: NoteRepository) = GetAllNotesCase(noteRepository)

    @Provides
    @NoteScope
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository) = DeleteNoteUseCase(noteRepository)

    @Provides
    @NoteScope
    fun provideGetNoteDetailUseCase(noteRepository: NoteRepository) = GetNoteDetailCase(noteRepository)

    @Provides
    @NoteScope
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository = NoteRepositoryImpl(noteDao)

    @Provides
    @NoteScope
    fun provideNotesAdapter() = NotesAdapter(fragment)
}