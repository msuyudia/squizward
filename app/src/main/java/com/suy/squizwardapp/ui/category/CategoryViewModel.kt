package com.suy.squizwardapp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.squizwardapp.R
import com.suy.squizwardapp.data.database.DatabaseManager
import com.suy.squizwardapp.data.entities.Category
import com.suy.squizwardapp.utils.Resource
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val categories by lazy { MutableLiveData<Resource<List<Category>?>>() }

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            categories.postValue(Resource.loading())
            val categoriesLocal = DatabaseManager.db.categoryDao().getCategory()
            when (categoriesLocal.isNullOrEmpty()) {
                true -> categories.postValue(Resource.error(R.string.text_empty_categories))
                false -> categories.postValue(Resource.success(categoriesLocal))
            }
        }
    }

    fun getCategories(): LiveData<Resource<List<Category>?>> {
        return categories
    }
}
