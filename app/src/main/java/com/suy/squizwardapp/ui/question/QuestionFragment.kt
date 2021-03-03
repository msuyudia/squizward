package com.suy.squizwardapp.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.google.gson.Gson
import com.suy.squizwardapp.R
import com.suy.squizwardapp.data.entities.Question
import com.suy.squizwardapp.databinding.FragmentQuestionBinding
import com.suy.squizwardapp.ui.result.ResultActivity
import com.suy.squizwardapp.utils.EXTRA_QUESTION
import com.suy.squizwardapp.utils.visible

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf {
            it.containsKey(EXTRA_QUESTION).apply {
                val question = Gson().fromJson(it.getString(EXTRA_QUESTION), Question::class.java)
                when (question?.isImageQuestion) {
                    1 -> {
                        binding?.ivQuestionFragment?.load(question.questionImage) {
                            placeholder(R.drawable.bg_loading)
                            error(R.drawable.bg_image_error)
                        }
                    }
                }
                binding?.tvQuestionFragment?.text = question?.questionText
                when (question.correctAnswer?.contains(",")) {
                    false -> onlyOneAnswer(question)
                    true -> multipleAnswers(question)
                }
            }
        }
    }

    private fun onlyOneAnswer(question: Question?) {
        binding?.rgAnswers?.visible()
        binding?.rbAnswerA?.text = question?.answerA
        binding?.rbAnswerB?.text = question?.answerB
        binding?.rbAnswerC?.text = question?.answerC
        binding?.rbAnswerD?.text = question?.answerD
        binding?.rgAnswers?.setOnCheckedChangeListener { rg, _ ->
            when (rg?.checkedRadioButtonId) {
                R.id.rb_answer_a -> ResultActivity.answers[question?.id ?: 0] = "A"
                R.id.rb_answer_b -> ResultActivity.answers[question?.id ?: 0] = "B"
                R.id.rb_answer_c -> ResultActivity.answers[question?.id ?: 0] = "C"
                R.id.rb_answer_d -> ResultActivity.answers[question?.id ?: 0] = "D"
            }
        }
    }

    private fun multipleAnswers(question: Question?) {
        binding?.containerCbAnswers?.visible()
        binding?.cbAnswerA?.text = question?.answerA
        binding?.cbAnswerB?.text = question?.answerB
        binding?.cbAnswerC?.text = question?.answerC
        binding?.cbAnswerD?.text = question?.answerD
        binding?.cbAnswerA?.setOnCheckedChangeListener { _, isChecked ->
            checkAnswer(question?.id, isChecked, "A")
        }
        binding?.cbAnswerB?.setOnCheckedChangeListener { _, isChecked ->
            checkAnswer(question?.id, isChecked, "B")
        }
        binding?.cbAnswerC?.setOnCheckedChangeListener { _, isChecked ->
            checkAnswer(question?.id, isChecked, "C")
        }
        binding?.cbAnswerD?.setOnCheckedChangeListener { _, isChecked ->
            checkAnswer(question?.id, isChecked, "D")
        }
    }

    private fun checkAnswer(id: Int?, isChecked: Boolean, answer: String) {
        val answers = StringBuilder()
        val result = ResultActivity.answers[id ?: 0]
        when (isChecked) {
            true -> {
                when (result != null) {
                    true -> when (result.contains(answer)) {
                        false -> {
                            val updateAnswer = answers.append(result).append(",$answer")
                            ResultActivity.answers[id ?: 0] = updateAnswer.toString()
                        }
                    }
                    false -> ResultActivity.answers[id ?: 0] = answer
                }
            }
            false -> {
                val split = result?.split(",")
                split?.forEachIndexed { index, splitAnswer ->
                    when (index == 0) {
                        true -> when (splitAnswer.contains(answer)) {
                            true -> {
                                ResultActivity.answers[id ?: 0] = result.replace("$answer,", "")
                                return@forEachIndexed
                            }
                        }
                        false -> when (splitAnswer.contains(answer)) {
                            true -> {
                                ResultActivity.answers[id ?: 0] = result.replace(",$answer", "")
                                return@forEachIndexed
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}