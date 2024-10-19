package ir.mahan.wikigames.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.databinding.ListBannerBinding
import javax.inject.Inject

class CarouselAdapter @Inject constructor(): RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    private lateinit var binding: ListBannerBinding
    private var games = emptyList<ResponseGamesList.Result>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselAdapter.ViewHolder {
        context = parent.context
        binding = ListBannerBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: CarouselAdapter.ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(games[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = games.size

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGamesList.Result) {
            binding.apply {
                listItemImage.load(item.backgroundImage)
                gameTitle.text = item.name
            }
        }
    }

    private var onItemClickListener: ((ResponseGamesList.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseGamesList.Result) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseGamesList.Result>) {
        val moviesDiffUtil = MoviesDiffUtils(games, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        games = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(private val oldItem: List<ResponseGamesList.Result>, private val newItem: List<ResponseGamesList.Result>) : DiffUtil.Callback() {
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