package com.bittelasia.vermillion.domain.model.theme.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme")
data class Theme(
    @PrimaryKey
    val bg: String
)