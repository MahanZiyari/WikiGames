package ir.mahan.wikigames.ui.games.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.mahan.wikigames.databinding.LoadMoreBinding

class LoadMoreAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {

    private lateinit var binding: LoadMoreBinding

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadMoreAdapter.ViewHolder {
        binding = LoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(retry)
    }

    override fun onBindViewHolder(holder: LoadMoreAdapter.ViewHolder, loadState: LoadState) {
        holder.binData(loadState)
    }

    inner class ViewHolder(retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.loadMoreRetry.setOnClickListener { retry() }
        }

        fun binData(state: LoadState) {
            //InitViews
            binding.apply {
                loadMoreProgress.isVisible = state is LoadState.Loading
                loadMoreTxt.isVisible = state is LoadState.Error
                loadMoreRetry.isVisible = state is LoadState.Error
            }
        }
    }
}