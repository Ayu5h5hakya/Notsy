package com.example.domain.model

import com.example.domain.ListElement

/**
 * Created by ayush on 2/12/18.b
 */
data class Note(var id : Long? = -1L,
                var textContent: String = "",
                var imageContent : String = "",
                var dateStamp : String = "",
                var timeStamp : String = ""): ListElement {

    override fun getType() = if(imageContent.isEmpty()) ListElement.ITEM_NOTE else ListElement.ITEM_NOTEWIMAGE

}