package ir.mahan.wikigames.ui.details.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.mahan.wikigames.data.model.ResponseGameDetails
import ir.mahan.wikigames.databinding.CardWithTextLayoutBinding
import javax.inject.Inject

class CardTextAdapter @Inject constructor() : RecyclerView.Adapter<CardTextAdapter.ViewHolder>() {

    private lateinit var binding: CardWithTextLayoutBinding
    private var items = emptyList<ResponseGameDetails.Genre>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = CardWithTextLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(items[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGameDetails.Genre) {
            val shadowColorFilter = Color.argb(150, 0, 0, 0)
            binding.apply {
                cardText.text = item.name

                root.setOnClickListener { root ->
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((ResponseGameDetails.Genre) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseGameDetails.Genre) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseGameDetails.Genre>) {
        val moviesDiffUtil = MoviesDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(
        private val oldItem: List<ResponseGameDetails.Genre>,
        private val newItem: List<ResponseGameDetails.Genre>
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