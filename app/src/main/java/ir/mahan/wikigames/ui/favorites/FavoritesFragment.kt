package ir.mahan.wikigames.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.databinding.FragmentFavoritesBinding
import ir.mahan.wikigames.ui.favorites.adapter.FullWidthItemGameAdapter
import ir.mahan.wikigames.utils.debugLog
import javax.inject.Inject


@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesContracts.View {

    // Binding Object
    private lateinit var binding: FragmentFavoritesBinding

    @Inject
    lateinit var presenter: FavoritesPresenter
    @Inject
    lateinit var favAdapter: FullWidthItemGameAdapter
    @Inject
    lateinit var entity: GameEntity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Db Query
        presenter.getAllFavoritesGames()
        // Handling UI
        binding.apply {
            // Games Recycler
            favRecycler.apply {
                adapter = favAdapter.also {
                    it.setOnItemClickListener {
                        findNavController().navigate(
                            FavoritesFragmentDirections.actionToDetailsfragment(it.id)
                        )
                    }
                }
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            // Explore Button
            emptyLay.txtExplore.setOnClickListener {
                findNavController().navigate(
                    FavoritesFragmentDirections.actionToGamesfragment(
                        category = "All",
                        title = "Explore",
                        id = 0
                    )
                )
            }

        }
    }

    override fun showAllFavoritesGames(games: List<GameEntity>) {
        favAdapter.setData(games)
        binding.apply {
            favRecycler.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }

    override fun showGeneralError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showEmptyScreen() {
        binding.apply {
            favRecycler.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
    }

}