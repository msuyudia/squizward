package com.suy.squizwardapp.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Category(
    @PrimaryKey @ColumnInfo(name = "ID") val id: Int,
    @ColumnInfo(name = "Image") val image: String?,
    @ColumnInfo(name = "Name") val name: String?
) : Parcelable
