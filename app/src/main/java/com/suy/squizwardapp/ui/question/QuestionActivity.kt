package com.suy.squizwardapp.ui.question

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suy.squizwardapp.R
import com.suy.squizwardapp.base.BaseActivity
import com.suy.squizwardapp.data.entities.Category
import com.suy.squizwardapp.databinding.ActivityQuestionBinding
import com.suy.squizwardapp.dialog.BottomSheetAlertDialog
import com.suy.squizwardapp.listener.AlertListener
import com.suy.squizwardapp.ui.result.ResultActivity
import com.suy.squizwardapp.utils.Resource
import com.suy.squizwardapp.utils.visible
import splitties.activities.start
import splitties.toast.longToast
import java.util.concurrent.TimeUnit

class QuestionActivity : BaseActivity(), AlertListener {
    companion object {
        const val EXTRA_CATEGORY = "category"
    }

    private lateinit var binding: ActivityQuestionBinding
    private var cdt: CountDownTimer? = null
    private val tenMinutes by lazy { 600000L }
    private val viewModel by viewModels<QuestionViewModel>()
    private val btsAlert by lazy { BottomSheetAlertDialog(this) }
    private val finishTag by lazy { "finish" }
    private val exitTag by lazy { "exit" }
    private val category by lazy { intent.getParcelableExtra<Category>(EXTRA_CATEGORY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initiateViewModel()
    }

    private fun initiateViewModel() {
        viewModel.getQuestions(category?.id ?: 0)
        viewModel.questions().observe(this, { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    initiateUI()
                    binding.tvTotalQuestion.text = getString(
                        R.string.text_total_question_value,
                        1,
                        resource.data?.size
                    )
                    cdt = setTimer
                    binding.vpQuestion.adapter =
                        QuestionAdapter(this, resource.data ?: mutableListOf())
                    TabLayoutMediator(binding.tlQuestion, binding.vpQuestion) { tab, position ->
                        tab.text = getString(R.string.text_question_value, position.plus(1))
                    }.attach()
                    binding.tlQuestion.addOnTabSelectedListener(object :
                        TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            binding.tvTotalQuestion.text = getString(
                                R.string.text_total_question_value,
                                tab?.position?.plus(1),
                                resource.data?.size
                            )
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {
                        }
                    })
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
        supportActionBar?.title = category?.name
        binding.fabEndQuestion.visible()
        binding.fabEndQuestion.setOnClickListener {
            if (btsAlert.isAdded) return@setOnClickListener
            btsAlert.setTitle(R.string.title_alert_quiz_finish)
            btsAlert.show(supportFragmentManager, finishTag)
        }
    }

    private val setTimer by lazy {
        object : CountDownTimer(tenMinutes, 1000) {
            override fun onTick(millis: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
                    .minus(TimeUnit.MINUTES.toSeconds(minutes))
                when (minutes == 0L) {
                    true -> binding.cvTimerQuestion.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@QuestionActivity,
                            R.color.colorRed
                        )
                    )
                }
                binding.tvTimerQuestion.text = getString(
                    R.string.text_timer_value,
                    minutes.textTime(),
                    seconds.textTime()
                )
            }

            override fun onFinish() {
                this@QuestionActivity.finish()
                start<ResultActivity> {
                    putExtra(ResultActivity.EXTRA_CATEGORY, category)
                }
            }
        }.start()
    }

    private fun Long.textTime(): String {
        return when (toString().length == 2) {
            true -> toString()
            false -> "0$this"
        }
    }

    override fun onAlertClicked(yes: Boolean) {
        when (yes) {
            true -> when (btsAlert.tag?.matches(finishTag.toRegex())) {
                true -> cdt?.onFinish()
                false -> super.onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        if (btsAlert.isAdded) return
        btsAlert.setTitle(R.string.title_alert_exit_quiz)
        btsAlert.show(supportFragmentManager, exitTag)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (cdt != null) cdt?.cancel()
        ResultActivity.answers.clear()
    }
}