package com.suy.squizwardapp.data.entities

data class Result(val question: Question?, val userAnswer: String?) {
    fun fullAnswer(isUser: Boolean): String {
        val fullAnswer = when (isUser) {
            true -> userAnswer
            false -> question?.correctAnswer
        }
        return when (fullAnswer?.contains(",")) {
            true -> {
                val fullMultipleAnswers = StringBuilder()
                val splitFullAnswer = fullAnswer.split(",")
                splitFullAnswer.forEachIndexed { index, answer ->
                    when (index != splitFullAnswer.lastIndex) {
                        true -> fullMultipleAnswers.append("${fullOnlyOneAnswer(answer)}\n")
                        false -> fullMultipleAnswers.append(fullOnlyOneAnswer(answer))
                    }
                }
                fullMultipleAnswers.toString()
            }
            false -> fullOnlyOneAnswer(fullAnswer)
            else -> ""
        }
    }

    private fun fullOnlyOneAnswer(answer: String?): String {
        return when (answer) {
            "A" -> "${question?.answerA}"
            "B" -> "${question?.answerB}"
            "C" -> "${question?.answerC}"
            "D" -> "${question?.answerD}"
            else -> ""
        }
    }
}
