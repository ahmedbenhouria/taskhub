package com.task.hub.data.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.task.hub.presentation.ui.screen.add.Member

class ListConvertor {

    @TypeConverter
    fun listToJsonString(value: List<Member>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Member>::class.java).toList()
}