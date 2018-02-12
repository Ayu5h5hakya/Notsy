package com.example.domain.model

/**
 * Created by ayush on 2/12/18.
 */
@Entity(tableName = "DashboardRow")
data class Note(val id : Int = -1, val noteText : String = "")