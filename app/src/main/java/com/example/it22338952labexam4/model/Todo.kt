package com.example.it22338952labexam4.model

import android.icu.text.CaseMap.Title
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todoes")
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val todoTitle: String,
    val todoDesc: String
): Parcelable
