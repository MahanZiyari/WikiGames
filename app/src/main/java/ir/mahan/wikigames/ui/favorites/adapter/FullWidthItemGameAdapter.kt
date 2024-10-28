package ir.mahan.wikigames.ui.favorites.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.mahan.wikigames.R
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.databinding.ItemGameFavBinding
import ir.mahan.wikigames.databinding.ItemGamesSmallBinding
import ir.mahan.wikigames.utils.loadByFade
import javax.inject.Inject

class FullWidthItemGameAdapter @Inject constructor() :
    RecyclerView.Adapter<FullWidthItemGameAdapter.ViewHolder>() {

    private lateinit var binding: ItemGameFavBinding
    private var games = emptyList<GameEntity>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = ItemGameFavBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder()
    }


    override fun onBindViewHolder(holder: FullWidthItemGameAdapter.ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(games[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = games.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GameEntity) {
            binding.apply {
                imgCover.loadByFade(item.backgroundimage)
                txtTitle.text = item.title
                txtDesc.text = item.description
                txtMetaScore.text = item.meta
                genreCard.cardText.text = item.genres.first()
                val score = item.meta.toIntOrNull()
                when (score) {
                    in 80..100 -> {
                        txtMeta.setTextColor(Color.GREEN)
                        txtMetaScore.setTextColor(Color.GREEN)
                    }
                    in 60 until 80 -> {
                        txtMeta.setTextColor(context.getColor(R.color.RoyalOrange))
                        txtMetaScore.setTextColor(context.getColor(R.color.RoyalOrange))
                    }
                    in 0 until 60 -> {
                        txtMeta.setTextColor(Color.RED)
                        txtMetaScore.setTextColor(Color.RED)
                    }
                }

                root.setOnClickListener { root ->
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((GameEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (GameEntity) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<GameEntity>) {
        val moviesDiffUtil = MoviesDiffUtils(games, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        games = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(
        private val oldItem: List<GameEntity>,
        private val newItem: List<GameEntity>
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