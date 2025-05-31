package com.ismoil.tasbeh.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ismoil.tasbeh.model.Zikrlar


@Entity(tableName = "user")
data class User(


    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var coin:Int,
    var bg1: Boolean,
    var bg2: Boolean,
    var bg3: Boolean,
    var bg4: Boolean,
    var bg5: Boolean,
    var bg6: Boolean,
    var bg7: Boolean,

    var bg8: Boolean,
    var bg9: Boolean,
    var bg10: Boolean,









) {
}