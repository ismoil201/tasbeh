package com.ismoil.tasbeh.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ismoil.tasbeh.room.dao.UserDao
import com.ismoil.tasbeh.room.dao.ZikrDao
import com.ismoil.tasbeh.room.entity.User
import com.ismoil.tasbeh.room.entity.Zikr


@Database(entities = [Zikr::class, User::class], version = 6)
@TypeConverters(Converters::class)
abstract class AppDataBase(): RoomDatabase() {

    abstract fun zikrDao():ZikrDao
    abstract fun userDao():UserDao

    companion object{
        private var instance:AppDataBase ?=null

        @Synchronized
        fun getInstance(context:Context): AppDataBase{
            if (instance == null){
                instance =
                    Room.databaseBuilder(context, AppDataBase::class.java, "Tasbeh_db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }

}