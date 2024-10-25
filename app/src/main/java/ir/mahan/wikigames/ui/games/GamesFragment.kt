package ir.mahan.wikigames.ui.games

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.mahan.wikigames.R
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.databinding.FragmentGamesBinding
import ir.mahan.wikigames.ui.search.adapter.GameItemAdapter
import javax.inject.Inject


@AndroidEntryPoint
class GamesFragment : Fragment(), GamesContract.View {

    // Binding object
    private lateinit var binding: FragmentGamesBinding
    // Args
    private val args: GamesFragmentArgs by navArgs()
    private var type = ""
    private var typeId = 0

    @Inject lateinit var gameItemAdapter: GameItemAdapter
    @Inject lateinit var presenter: GamesPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = args.category
        typeId = args.id
        // Api Call
        presenter.getRequestedGames(
            category = type,
            categoryId = typeId
        )
        // Handle UI
        binding.apply {
            gamesToolbar.title = type
            gamesToolbar.setTitleTextColor(Color.WHITE)
            gamesToolbar.setNavigationIconTint(Color.WHITE)
            gamesToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
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

    override fun showRequestedGames(games: List<ResponseGamesList.Result>) {
        gameItemAdapter.setData(games)
        binding.gamesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gameItemAdapter
        }
    }

    override fun showGeneralError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}