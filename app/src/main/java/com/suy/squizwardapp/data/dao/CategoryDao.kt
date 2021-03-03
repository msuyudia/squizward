package com.suy.squizwardapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.suy.squizwardapp.data.entities.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    suspend fun getCategory(): List<Category>
}