package ir.mahan.wikigames.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ir.mahan.wikigames.databinding.ListBannerBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor(): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private lateinit var binding: ListBannerBinding
    private var images = emptyList<String>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        context = parent.context
        binding = ListBannerBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(images[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.listItemImage.load(item)
        }
    }

    fun setData(data: List<String>) {
        val moviesDiffUtil = MoviesDiffUtils(images, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        images = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(private val oldItem: List<String>, private val newItem: List<String>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}