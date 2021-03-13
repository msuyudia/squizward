package com.suy.squizwardapp.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suy.squizwardapp.R
import com.suy.squizwardapp.data.entities.Result
import com.suy.squizwardapp.databinding.ItemAnswerBinding
import com.suy.squizwardapp.utils.gone
import com.suy.squizwardapp.utils.visible

class AnswerAdapter(private val list: List<Result>) :
    RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {
    private val listAnswerOpened by lazy { mutableListOf<Int>() }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    inner class ViewHolder(private val binding: ItemAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            with(binding) {
                showDetailAnswer(result)
                when (result.question?.isImageQuestion) {
                    1 -> ivQuestionAnswer.load(result.question.questionImage) {
                        placeholder(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.bg_loading
                            )
                        )
                        error(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.bg_image_error
                            )
                        )
                    }
                }
                tvQuestionAnswer.text = result.question?.questionText
                when (listAnswerOpened.contains(adapterPosition)) {
                    true -> detailAnswerVisible(binding)
                    false -> detailAnswerHiden(binding)
                }
                itemView.setOnClickListener {
                    when (containerDetailAnswer.isVisible) {
                        false -> {
                            listAnswerOpened.add(adapterPosition)
                            detailAnswerVisible(binding)
                        }
                        true -> {
                            listAnswerOpened.remove(adapterPosition)
                            detailAnswerHiden(binding)
                        }
                    }
                }
            }
        }

        private fun detailAnswerHiden(binding: ItemAnswerBinding) {
            with(binding) {
                ivArrowAnswer.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_drop_down
                    )
                )
                containerDetailAnswer.gone()
            }
        }

        private fun detailAnswerVisible(binding: ItemAnswerBinding) {
            with(binding) {
                ivArrowAnswer.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_drop_up
                    )
                )
                containerDetailAnswer.visible()
            }
        }

        private fun showDetailAnswer(result: Result?) {
            val correctAnswer = result?.question?.correctAnswer
            val userAnswer = result?.userAnswer
            when (userAnswer.isNullOrEmpty()) {
                true -> showEmptyUserAnswer(result)
                false -> when (correctAnswer?.contains(",")) {
                    true -> {
                        var isSame = true
                        val splitUserAnswer = userAnswer.split(",")
                        splitUserAnswer.forEach {
                            isSame = correctAnswer.contains(it)
                            if (!isSame) {
                                showWrongUserAnswer(result)
                                return
                            }
                        }
                        when (userAnswer.length == correctAnswer.length && isSame) {
                            true -> showCorrectUserAnswer(result)
                            false -> showWrongUserAnswer(result)
                        }
                    }
                    false -> when (correctAnswer.contains(userAnswer)) {
                        true -> showCorrectUserAnswer(result)
                        false -> showWrongUserAnswer(result)
                    }
                }
            }
        }

        private fun showWrongUserAnswer(result: Result?) {
            with(binding) {
                tvTitleAnswer.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorRed
                    )
                )
                tvTitleAnswer.text =
                    itemView.context.getString(
                        R.string.text_answer_value,
                        adapterPosition.plus(1),
                        "Incorrect"
                    )
                tvUserAnswer.text =
                    itemView.context.getString(
                        R.string.text_user_answer_wrong_value,
                        result?.fullAnswer(true)
                    )
                tvCorrectAnswer.text = itemView.context.getString(
                    R.string.text_correction_right_answer_value,
                    result?.fullAnswer(false)
                )
            }
        }

        private fun showEmptyUserAnswer(result: Result?) {
            with(binding) {
                tvTitleAnswer.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorRed
                    )
                )
                tvTitleAnswer.text =
                    itemView.context.getString(
                        R.string.text_answer_value,
                        adapterPosition.plus(1),
                        "Incorrect"
                    )
                tvUserAnswer.visible()
                tvUserAnswer.text = itemView.context.getString(R.string.text_user_answer_empty)
                tvCorrectAnswer.text = itemView.context.getString(
                    R.string.text_correction_right_answer_value,
                    result?.fullAnswer(false)
                )
            }
        }

        private fun showCorrectUserAnswer(result: Result?) {
            with(binding) {
                tvTitleAnswer.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorGreen
                    )
                )
                tvTitleAnswer.text =
                    itemView.context.getString(
                        R.string.text_answer_value,
                        adapterPosition.plus(1),
                        "Correct"
                    )
                tvUserAnswer.gone()
                tvCorrectAnswer.text =
                    itemView.context.getString(
                        R.string.text_user_answer_correct_value,
                        result?.fullAnswer(true)
                    )
            }
        }
    }
}
