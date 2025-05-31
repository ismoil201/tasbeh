package com.ismoil.tasbeh.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ismoil.tasbeh.room.entity.User
import com.ismoil.tasbeh.room.entity.Zikr

@Dao
interface UserDao {


    @Query("SELECT * FROM User")
    fun getUser(): User

    @Query("select coin from user")
    fun getCoin():Int

    @Insert
    fun addUser(user:User)

    @Update
    fun updateUser(user:User)

}