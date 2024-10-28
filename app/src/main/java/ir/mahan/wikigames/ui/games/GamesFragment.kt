package ir.mahan.wikigames.ui.games

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.databinding.FragmentGamesBinding
import ir.mahan.wikigames.ui.games.adapter.LoadMoreAdapter
import kotlinx.coroutines.rx3.asFlowable
import javax.inject.Inject


@AndroidEntryPoint
class GamesFragment : Fragment(), GamesContract.View {

    // Binding object
    private lateinit var binding: FragmentGamesBinding
    // Args
    private val args: GamesFragmentArgs by navArgs()
    private var type = ""
    private var title = ""
    private var typeId = 0

    @Inject
    lateinit var gameItemAdapter: PagingGamesAdapter
    @Inject lateinit var presenter: GamesPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = args.category
        typeId = args.id
        title = args.title
        // Api Call
        presenter.getRequestedGames(
            category = type,
            categoryId = typeId
        )
        // Handle UI
        binding.apply {
            setupToolbar()

            // Adapter Config
            gameItemAdapter.apply {

                setOnItemClickListener {
                    val direction = GamesFragmentDirections.actionToDetailsfragment(it.id)
                    findNavController().navigate(direction)
                }

                loadStateFlow
                    .asFlowable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        gamesProgress.isVisible = it.refresh is LoadState.Loading
                    }

            }

            // RecyclerView Config
            gamesRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = gameItemAdapter.withLoadStateFooter(
                    LoadMoreAdapter{
                        gameItemAdapter.retry()
                    }
                )
            }

        }
    }

    private fun FragmentGamesBinding.setupToolbar() {
        gamesToolbar.title = title
        gamesToolbar.setTitleTextColor(Color.WHITE)
        gamesToolbar.setNavigationIconTint(Color.WHITE)
        gamesToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun handleLoadingState(isShown: Boolean) {
        if (isShown) {
            binding.gamesRecycler.visibility = View.GONE
            binding.gamesProgress.visibility = View.VISIBLE
        } else {
            binding.gamesRecycler.visibility = View.VISIBLE
            binding.gamesProgress.visibility = View.GONE
        }
    }


    override fun loadGamesByPaging(games: PagingData<ResponseGamesList.Result>) {
        gameItemAdapter.submitData(lifecycle, games)
    }

    override fun showGeneralError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}