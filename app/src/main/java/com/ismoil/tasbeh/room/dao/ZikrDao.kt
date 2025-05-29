package com.ismoil.tasbeh.room.dao
import com.ismoil.tasbeh.model.ZikrCountPerDay

import androidx.room.*
import com.ismoil.tasbeh.room.entity.Zikr

@Dao
interface ZikrDao {

    @Insert
    fun addZikr(zikr: Zikr)

    @Delete
    fun deleteZikr(zikr: Zikr)

    @Update
    fun updateZikr(zikr: Zikr)

    @Query("SELECT * FROM Zikrs")
    fun getZikrs(): List<Zikr>

    @Query("SELECT * FROM Zikrs WHERE id = :id")
    fun getZikrById(id: Int): Zikr

    @Query("""
    SELECT date AS date, SUM(currentCount) AS totalCount 
    FROM Zikrs 
    WHERE date >= :startDate 
    GROUP BY date 
    ORDER BY date ASC
    LIMIT 10
""")
    fun getLast10DaysZikrCounts(startDate: String): List<ZikrCountPerDay>


    @Query("""
    SELECT date, SUM(currentCount) as totalCount
    FROM Zikrs
    GROUP BY date
    ORDER BY date DESC
    LIMIT 10
""")
    fun getLast10ZikrCounts(): List<ZikrCountPerDay>


    // 🔹 1. Bugungi kun uchun zikrlar soni
    @Query("""
        SELECT SUM(currentCount) FROM Zikrs 
        WHERE date = :todayDate
    """)
    fun getZikrsCountByDay(todayDate: String): Int?

    // 🔹 2. So'nggi 7 kun uchun zikrlar soni
    @Query("""
        SELECT SUM(currentCount) FROM Zikrs 
        WHERE date >= :weekAgoDate
    """)
    fun getZikrsCountByWeek(weekAgoDate: String): Int?

    // 🔹 3. So'nggi 30 kun (oy) uchun zikrlar soni
    @Query("""
        SELECT SUM(currentCount) FROM Zikrs 
        WHERE date >= :monthAgoDate
    """)
    fun getZikrsCountByMonth(monthAgoDate: String): Int?
}
