package com.ismoil.tasbeh.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ismoil.tasbeh.room.entity.Zikr

@Dao
interface ZikrDao {

    @Insert
    fun addZikr(zikr: Zikr)

    @Delete
    fun deleteZikr(zikr: Zikr)

    @Update
    fun updateZikr(zikr: Zikr)

    @Query("select * from  Zikrs")
    fun getZikrs():List<Zikr>

    @Query("select * from zikrs where id =:id")
    fun getZikrById(id:Int):Zikr

}