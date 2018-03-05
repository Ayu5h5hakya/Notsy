package com.example.data.dataModel

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by ayush on 2/14/18.
 */
@Entity(tableName = "Notes")
data class NoteModel (
        @PrimaryKey(autoGenerate = true) var id : Long? = null,
        var textContent : String = "",
        var imageContent : String = "")