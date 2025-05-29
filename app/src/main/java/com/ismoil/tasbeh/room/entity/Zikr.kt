package com.ismoil.tasbeh.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ismoil.tasbeh.model.Zikrlar


@Entity(tableName = "Zikrs")
data class Zikr(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "Zikr nomi")
    var zirk : Zikrlar,
    var countPresent : Int,
    var date : String,
    var currentCount: Int,
    var maxCount: Int,
    var succsecc : Boolean


) {
}