package com.ismoil.tasbeh.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ismoil.tasbeh.model.Zikrlar

class Converters {

    @TypeConverter
    fun fromZikrlar(zikrlar: Zikrlar): String {
        return Gson().toJson(zikrlar)
    }

    @TypeConverter
    fun toZikrlar(data: String): Zikrlar {
        return Gson().fromJson(data, Zikrlar::class.java)
    }
}