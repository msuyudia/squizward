package com.suy.squizwardapp.ui.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.squizwardapp.R
import com.suy.squizwardapp.data.database.DatabaseManager
import com.suy.squizwardapp.data.entities.Question
import com.suy.squizwardapp.utils.Resource
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private val questions by lazy { MutableLiveData<Resource<List<Question>?>>() }

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

    fun questions(): LiveData<Resource<List<Question>?>> = questions
}
