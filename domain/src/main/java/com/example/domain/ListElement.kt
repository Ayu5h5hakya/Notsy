package com.example.domain

/**
 * Created by ayush on 2/27/18.
 */
interface ListElement{

    companion object {
        val ITEM_DATE = 0
        val ITEM_NOTE = 1
        val ITEM_NOTEWIMAGE = 2
    }

    fun getType() : Int
}