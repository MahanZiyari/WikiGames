package ir.mahan.wikigames.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseStores
import ir.mahan.wikigames.databinding.ItemGamesSmallBinding
import ir.mahan.wikigames.databinding.ItemImageBgBinding
import ir.mahan.wikigames.ui.home.adapter.SmallItemGameAdapter
import ir.mahan.wikigames.utils.loadByFade
import javax.inject.Inject

class ImageAdapter @Inject constructor(): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private lateinit var binding: ItemImageBgBinding
    private var games = emptyList<ResponseStores.Result>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = ItemImageBgBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder()
    }


    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(games[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = games.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseStores.Result) {
            binding.apply {
                cover.loadByFade(item.imageBackground)
                title.text = item.name
            }
        }
    }

    private var onItemClickListener: ((ResponseStores.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseStores.Result) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseStores.Result>) {
        val moviesDiffUtil = MoviesDiffUtils(games, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        games = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(
        private val oldItem: List<ResponseStores.Result>,
        private val newItem: List<ResponseStores.Result>
    ) : DiffUtil.Callback() {
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