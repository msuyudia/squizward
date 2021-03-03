package com.suy.squizwardapp.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.suy.squizwardapp.R
import com.suy.squizwardapp.data.entities.Category
import com.suy.squizwardapp.data.type.CategoryType
import com.suy.squizwardapp.databinding.ItemCategoryBinding
import com.suy.squizwardapp.listener.PositionListener

class CategoryAdapter(private val list: List<Category>, private val listener: PositionListener) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            with(binding) {
                ivCategory.load(
                    ContextCompat.getDrawable(
                        binding.root.context, when (category.id - 1) {
                            CategoryType.ART_CULTURE.ordinal -> R.drawable.ic_art_culture
                            CategoryType.GEOGRAPHY.ordinal -> R.drawable.ic_geography
                            CategoryType.MUSIC.ordinal -> R.drawable.ic_music
                            CategoryType.ECONOMY.ordinal -> R.drawable.ic_economy
                            CategoryType.HISTORY.ordinal -> R.drawable.ic_history
                            CategoryType.NATURE.ordinal -> R.drawable.ic_nature
                            CategoryType.FILM_TV.ordinal -> R.drawable.ic_film_tv
                            CategoryType.INFORMATICS.ordinal -> R.drawable.ic_informatics
                            CategoryType.FOOD_AND_DRINK.ordinal -> R.drawable.ic_food_and_drink
                            CategoryType.LANGUAGE.ordinal -> R.drawable.ic_language
                            CategoryType.SCIENCE.ordinal -> R.drawable.ic_science
                            CategoryType.GENERAL.ordinal -> R.drawable.ic_general
                            CategoryType.LITERATURE.ordinal -> R.drawable.ic_literature
                            CategoryType.SPORTS.ordinal -> R.drawable.ic_sports
                            else -> R.drawable.ic_politics
                        }
                    )
                )
                tvCategory.text = category.name
                itemView.setOnClickListener { listener.onItemClicked(category) }
            }
        }
    }
}