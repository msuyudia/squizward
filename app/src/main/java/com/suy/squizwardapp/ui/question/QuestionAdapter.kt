package com.suy.squizwardapp.ui.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.gson.Gson
import com.suy.squizwardapp.data.entities.Question
import com.suy.squizwardapp.utils.EXTRA_QUESTION

class QuestionAdapter(fragmentActivity: FragmentActivity, private val list: List<Question>) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        val fragment = QuestionFragment()
        val question = list[position]
        fragment.arguments = Bundle().apply {
            putString(EXTRA_QUESTION, Gson().toJson(question))
        }
        return fragment
    }
}