package com.suy.squizwardapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey @ColumnInfo(name = "ID") val id: Int,
    @ColumnInfo(name = "QuestionText") val questionText: String?,
    @ColumnInfo(name = "QuestionImage") val questionImage: String?,
    @ColumnInfo(name = "AnswerA") val answerA: String?,
    @ColumnInfo(name = "AnswerB") val answerB: String?,
    @ColumnInfo(name = "AnswerC") val answerC: String?,
    @ColumnInfo(name = "AnswerD") val answerD: String?,
    @ColumnInfo(name = "CorrectAnswer") val correctAnswer: String?,
    @ColumnInfo(name = "IsImageQuestion") val isImageQuestion: Int?,
    @ColumnInfo(name = "CategoryID") val categoryId: Int?
)
