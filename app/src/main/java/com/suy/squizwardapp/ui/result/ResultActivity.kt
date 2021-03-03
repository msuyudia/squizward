package com.suy.squizwardapp.ui.result

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import com.suy.squizwardapp.R
import com.suy.squizwardapp.base.BaseActivity
import com.suy.squizwardapp.data.entities.Category
import com.suy.squizwardapp.data.entities.Result
import com.suy.squizwardapp.databinding.ActivityResultBinding
import com.suy.squizwardapp.utils.Resource
import splitties.toast.longToast
import kotlin.math.roundToInt

class ResultActivity : BaseActivity() {
    companion object {
        const val EXTRA_CATEGORY = "id"
        var answers = mutableMapOf<Int, String>()
    }

    private lateinit var binding: ActivityResultBinding
    private val viewModel by viewModels<ResultViewModel>()
    private val category by lazy { intent?.getParcelableExtra<Category>(EXTRA_CATEGORY) }
    private var totalAllAnswer: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateViewModel()
    }

    private fun initiateViewModel() {
        viewModel.getQuestions(category?.id ?: 0)
        viewModel.totalCorrectAnswer().observe(this, {
            val score = it.toFloat().div(totalAllAnswer?.toFloat() ?: 0F)
                .times(100F).roundToInt()
            with(binding) {
                tvScoreResult.text = getString(R.string.text_score_value, score)
                tvCorrectResult.text =
                    getString(R.string.text_correct_value, it)
            }
            when (it == totalAllAnswer) {
                true -> {
                    binding.tvTitleResult.text =
                        getString(R.string.title_perfect_result, category?.name)
                    MediaPlayer.create(this, R.raw.applause).start()
                }
                false -> binding.tvTitleResult.text =
                    getString(R.string.title_result, category?.name)
            }
        })
        viewModel.totalWrongAnswer().observe(this, {
            binding.tvWrongResult.text = getString(R.string.text_wrong_value, it)
        })
        viewModel.questions().observe(this, { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    initiateUI()
                    val questions = resource.data
                    totalAllAnswer = questions?.size
                    val results = mutableListOf<Result>()
                    val lastIndex = questions?.lastIndex ?: 1
                    for (i in 0..lastIndex) {
                        val question = questions?.get(i)
                        val questionId = question?.id
                        val result = Result(question, answers[questionId])
                        val isLastAnswer = i == lastIndex
                        results.add(i, result)
                        viewModel.checkAnswer(
                            question?.correctAnswer ?: "",
                            answers[questionId],
                            isLastAnswer
                        )
                    }
                    with(binding) {
                        tvTotalQuestionResult.text = getString(
                            R.string.text_total_question_value,
                            questions?.size,
                            questions?.size
                        )
                        rvAnswer.adapter = AnswerAdapter(results)
                    }
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    longToast(resource.message ?: 0)
                }
            }
        })
    }

    private fun initiateUI() {
        supportActionBar?.elevation = 0F
        supportActionBar?.title = getString(R.string.title_achievement)
    }
    //private fun calculateScore(correctAnswer: String, userAnswer: String?) {
    //    when (userAnswer.isNullOrEmpty()) {
    //        true -> totalWrongAnswer += 1
    //        false -> when (correctAnswer.contains(",")) {
    //            true -> {
    //                var isSame = true
    //                val splitUserAnswer = userAnswer.split(",")
    //                splitUserAnswer.forEach {
    //                    isSame = correctAnswer.contains(it)
    //                    if (!isSame) {
    //                        totalWrongAnswer += 1
    //                        return
    //                    }
    //                }
    //                when (userAnswer.length == correctAnswer.length && isSame) {
    //                    true -> totalCorrectAnswer += 1
    //                    false -> totalWrongAnswer += 1
    //                }
    //            }
    //            false -> when (correctAnswer.contains(userAnswer)) {
    //                true -> totalCorrectAnswer += 1
    //                false -> totalWrongAnswer += 1
    //            }
    //        }
    //    }
    //}
    override fun onDestroy() {
        super.onDestroy()
        answers.clear()
    }
}