package com.example.mvvm.model.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Entity(tableName = "notes")
@Parcelize
data class Note(
    @ColumnInfo(name = "note_title") val title: String,
    @ColumnInfo(name = "note_text") val text: String,
    @ColumnInfo(name = "note_date") val time: String = Date().toString(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) : Parcelable