package com.suy.squizwardapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suy.squizwardapp.data.dao.CategoryDao
import com.suy.squizwardapp.data.dao.QuestionDao
import com.suy.squizwardapp.data.entities.Category
import com.suy.squizwardapp.data.entities.Question

@Database(entities = [Category::class, Question::class], version = 1)
abstract class DatabaseApp : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun questionDao(): QuestionDao
}