package com.suy.squizwardapp.listener

import com.suy.squizwardapp.data.entities.Category

interface PositionListener {
    fun onItemClicked(category: Category)
}