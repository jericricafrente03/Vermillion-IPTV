package com.bittelasia.vermillion.domain.model.facilities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.vermillion.domain.model.facilities.item.FacilitiesData

@Dao
interface FacilitiesDao {
    @Query("SELECT * FROM facilities")
    fun getAllFacilities(): List<FacilitiesData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacilities(facilities: List<FacilitiesData>)

    @Query("DELETE FROM facilities")
    suspend fun deleteAllFacilities()
}
