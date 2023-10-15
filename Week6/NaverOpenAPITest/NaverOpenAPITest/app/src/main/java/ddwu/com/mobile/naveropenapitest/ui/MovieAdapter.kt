package ddwu.com.mobile.openapitest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.naveropenapitest.data.Book
import ddwu.com.mobile.naveropenapitest.databinding.ListItemBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    var books: List<Book>? = null

    override fun getItemCount(): Int {
        return books?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.itemBinding.tvItem.text = books?.get(position).toString()
    }

    class MovieHolder(val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
