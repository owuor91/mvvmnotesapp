package io.owuor91.mvvmnotesapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Note(@PrimaryKey val id: Int, val title: String?, val noteText: String?)