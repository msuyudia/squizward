package com.suy.squizwardapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.suy.squizwardapp.data.entities.Question

@Dao
interface QuestionDao {
    @Query("SELECT * FROM Question WHERE CategoryID = :id")
    suspend fun getQuestions(id: Int): List<Question>?
}