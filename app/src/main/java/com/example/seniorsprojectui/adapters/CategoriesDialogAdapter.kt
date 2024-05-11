package com.example.seniorsprojectui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.seniorsprojectui.R
import com.example.seniorsprojectui.backend.CategoryData


interface OnCategorySelection{
    fun onCategorySelected(categoryPosition : Int)
}

class CategoriesDialogAdapter(val categoriesClass : List<CategoryData>)  : RecyclerView.Adapter<CategoryListVH>() {

    private lateinit var interfaceListener : OnCategorySelection

    fun setOnCategoryClickListenerInterface(listener : OnCategorySelection)
    {
        interfaceListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListVH {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.sample_layout_category_item_in_dialog, parent, false)
        return CategoryListVH(view, interfaceListener)
    }

    override fun onBindViewHolder(holder: CategoryListVH, position: Int) {
        holder.cName.text = categoriesClass[position].categoryLabel
        holder.cImage.setImageResource(categoriesClass[position].categoryIcon)


        // dynamic colors of category icon
//        val categoryIconColor = when(categoriesClass[position].category){
//            "Business" -> R.color.business
//            "Personal" -> R.color.personal
//            "Education" -> R.color.education
//            "Travel" -> R.color.travel
//            else -> R.color.colorPrimary
//        }

//        holder.cImage.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context, categoryIconColor)




    }

    override fun getItemCount(): Int {
        return categoriesClass.size
    }
}

class CategoryListVH(itemView : View, listener : OnCategorySelection) : RecyclerView.ViewHolder(itemView)
{
    val cName = itemView.findViewById<TextView>(R.id.tvCategoryItem)
    val cImage = itemView.findViewById<ImageView>(R.id.ivCategoryItem)



    init {
        itemView.setOnClickListener {
            listener.onCategorySelected(adapterPosition)
        }
    }
    }
