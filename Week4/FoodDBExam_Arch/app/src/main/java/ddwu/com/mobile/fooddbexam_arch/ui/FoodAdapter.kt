package ddwu.com.mobile.fooddbexam_arch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.fooddbexam_arch.data.Food
import ddwu.com.mobile.fooddbexam_arch.databinding.ListItemBinding

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodHolder>() {
    var foods: List<Food>? = null

    override fun getItemCount(): Int {
        return foods?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        holder.itemBinding.tvItem.text = foods?.get(position).toString()
    }

    class FoodHolder(val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
