package ir.mahan.wikigames.ui.games

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.databinding.ItemGameBinding
import ir.mahan.wikigames.utils.loadByFade
import javax.inject.Inject

class PagingGamesAdapter @Inject constructor() :
    PagingDataAdapter<ResponseGamesList.Result, PagingGamesAdapter.ViewHolder>(
        differCallback
    ) {

    private lateinit var binding: ItemGameBinding
    private var games = emptyList<ResponseGamesList.Result>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = ItemGameBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder()
    }


    override fun onBindViewHolder(holder: PagingGamesAdapter.ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(getItem(position)!!)
        //Not duplicate items
        holder.setIsRecyclable(false)
    }


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGamesList.Result) {
            binding.apply {
                item.backgroundImage?.let {
                    gameCover.loadByFade(item.backgroundImage)
                }
                gameTitle.text = item.name
                gameSummary.text = item.description
                setGenreAndTag(item)
                setPlatforms(item)

                root.setOnClickListener { root ->
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }

        private fun ItemGameBinding.setPlatforms(item: ResponseGamesList.Result) {
            val parentPlatforms = item.parentPlatforms
            parentPlatforms.forEach {
                when (it.platform.id) {
                    1 -> windowPlatform.visibility = View.VISIBLE
                    2 -> psPlatform.visibility = View.VISIBLE
                    3 -> xboxPlatform.visibility = View.VISIBLE
                    4 -> applePlatform.visibility = View.VISIBLE
                    8 -> androidPlatform.visibility = View.VISIBLE
                    7 -> nintendoPlatform.visibility = View.VISIBLE
                }
            }
        }


        private fun ItemGameBinding.setGenreAndTag(item: ResponseGamesList.Result) {
            if (item.genres.isNotEmpty()) {
                gameGenre.text = item.genres.first().name
            } else {
                gameGenre.visibility = View.GONE
            }
            if (item.tags.isNullOrEmpty().not()) {
                gameTag.text = item.tags.first().name
            } else {
                gameTag.visibility = View.GONE
            }
        }
    }

    private var onItemClickListener: ((ResponseGamesList.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseGamesList.Result) -> Unit) {
        onItemClickListener = listener
    }




    companion object {
        val differCallback = object : DiffUtil.ItemCallback<ResponseGamesList.Result>() {
            override fun areItemsTheSame(
                oldItem: ResponseGamesList.Result,
                newItem: ResponseGamesList.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResponseGamesList.Result,
                newItem: ResponseGamesList.Result
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}