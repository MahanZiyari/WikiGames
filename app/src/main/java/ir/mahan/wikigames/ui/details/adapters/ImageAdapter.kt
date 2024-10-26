package ir.mahan.wikigames.ui.details.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.mahan.wikigames.data.model.ResponseScreenshots
import ir.mahan.wikigames.data.model.ResponseStores
import ir.mahan.wikigames.databinding.ItemImageBgBinding
import ir.mahan.wikigames.utils.debugLog
import ir.mahan.wikigames.utils.loadByFade
import javax.inject.Inject

class ImageAdapter @Inject constructor(): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private lateinit var binding: ItemImageBgBinding
    private var games = emptyList<ResponseScreenshots.Result>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = ItemImageBgBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(games[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = games.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseScreenshots.Result) {
            val shadowColorFilter = Color.argb(150, 0, 0, 0)
            binding.apply {
                cover.loadByFade(item.image)
                //cover.setColorFilter(shadowColorFilter)

                root.setOnClickListener { root ->
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((ResponseScreenshots.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseScreenshots.Result) -> Unit) {
        debugLog("Clicked")
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseScreenshots.Result>) {
        val moviesDiffUtil = MoviesDiffUtils(games, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        games = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(
        private val oldItem: List<ResponseScreenshots.Result>,
        private val newItem: List<ResponseScreenshots.Result>
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