package com.suy.squizwardapp.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.squizwardapp.R
import com.suy.squizwardapp.data.database.DatabaseManager
import com.suy.squizwardapp.data.entities.Question
import com.suy.squizwardapp.utils.Resource
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {
    private val questions by lazy { MutableLiveData<Resource<List<Question>?>>() }
    private var totalCorrectAnswer = 0
    private var totalWrongAnswer = 0
    private val correctAnswer by lazy { MutableLiveData<Int>() }
    private val wrongAnswer by lazy { MutableLiveData<Int>() }

    fun getQuestions(id: Int) {
        viewModelScope.launch {
            questions.postValue(Resource.loading())
            val questionDao = DatabaseManager.db.questionDao()
            val localQuestion = questionDao.getQuestions(id)
            when (localQuestion.isNullOrEmpty()) {
                true -> questions.postValue(Resource.error(R.string.text_empty_question))
                false -> questions.postValue(Resource.success(localQuestion))
            }
        }
    }

    fun checkAnswer(correct: String, userAnswer: String?, isLastAnswer: Boolean) {
        when (userAnswer.isNullOrEmpty()) {
            true -> totalWrongAnswer += 1
            false -> when (correct.contains(",")) {
                true -> {
                    var isSame = true
                    val splitUserAnswer = userAnswer.split(",")
                    splitUserAnswer.forEach {
                        isSame = correct.contains(it)
                        if (!isSame) {
                            totalWrongAnswer += 1
                            return
                        }
                    }
                    when (userAnswer.length == correct.length && isSame) {
                        true -> totalCorrectAnswer += 1
                        false -> totalWrongAnswer += 1
                    }
                }
                false -> when (correct.contains(userAnswer)) {
                    true -> totalCorrectAnswer += 1
                    false -> totalWrongAnswer += 1
                }
            }
        }
        if (isLastAnswer) {
            correctAnswer.postValue(totalCorrectAnswer)
            wrongAnswer.postValue(totalWrongAnswer)
        }
    }

    fun questions(): LiveData<Resource<List<Question>?>> = questions
    fun totalCorrectAnswer(): LiveData<Int> = correctAnswer
    fun totalWrongAnswer(): LiveData<Int> = wrongAnswer
}
