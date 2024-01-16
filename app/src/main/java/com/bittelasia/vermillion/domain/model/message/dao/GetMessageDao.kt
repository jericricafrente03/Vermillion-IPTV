package com.bittelasia.vermillion.domain.model.message.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.vermillion.domain.model.message.item.GetMessageData

@Dao
interface GetMessageDao {
    @Query("SELECT * FROM get_message")
    fun getAllMessages(): List<GetMessageData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<GetMessageData>)

    @Query("DELETE FROM get_message")
    suspend fun deleteAllMessages()
}