package com.suy.squizwardapp.ui.category

import android.os.Bundle
import androidx.activity.viewModels
import com.suy.squizwardapp.R
import com.suy.squizwardapp.base.BaseActivity
import com.suy.squizwardapp.data.entities.Category
import com.suy.squizwardapp.databinding.ActivityCategoryBinding
import com.suy.squizwardapp.listener.PositionListener
import com.suy.squizwardapp.ui.question.QuestionActivity
import com.suy.squizwardapp.utils.Resource
import splitties.activities.start
import splitties.toast.longToast

class CategoryActivity : BaseActivity(), PositionListener {
    private lateinit var binding: ActivityCategoryBinding
    private val viewModel by viewModels<CategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateUI()
        initiateViewModel()
    }

    private fun initiateUI() {
        supportActionBar?.title = getString(R.string.title_category)
    }

    private fun initiateViewModel() {
        viewModel.getCategories().observe(this, { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    binding.rvCategory.adapter =
                        CategoryAdapter(resource.data ?: mutableListOf(), this)
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    longToast(resource.message ?: 0)
                }
            }
        })
    }

    override fun onItemClicked(category: Category) {
        start<QuestionActivity> {
            putExtra(QuestionActivity.EXTRA_CATEGORY, category)
        }
    }
}