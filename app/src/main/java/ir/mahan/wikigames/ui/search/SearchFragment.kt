package ir.mahan.wikigames.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseStores
import ir.mahan.wikigames.databinding.FragmentSearchBinding
import ir.mahan.wikigames.ui.games.GamesFragmentDirections
import ir.mahan.wikigames.ui.search.adapter.GameItemAdapter
import ir.mahan.wikigames.ui.search.adapter.GeneralItemAdapter
import ir.mahan.wikigames.utils.DEBUG_TAG
import ir.mahan.wikigames.utils.NINTENDO_IMAGE_URL
import ir.mahan.wikigames.utils.PC_IMAGE_URL
import ir.mahan.wikigames.utils.PLAYSTATION_IMAGE_URL
import ir.mahan.wikigames.utils.Platform
import ir.mahan.wikigames.utils.XBOX_IMAGE_URL
import ir.mahan.wikigames.utils.checkForEmptiness
import ir.mahan.wikigames.utils.loadByFade
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment(), SearchContracts.View {

    // Binding Object
    private lateinit var binding: FragmentSearchBinding

    @Inject lateinit var presenter: SearchPresenter
    @Inject lateinit var storesAdapter: GeneralItemAdapter
    @Inject lateinit var genresAdapter: GeneralItemAdapter
    @Inject lateinit var gameItemAdapter: GameItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Call Api
        presenter.getAllGenres()
        presenter.getAllStores()

        // Handling UI
        binding.apply {
            // Platforms
            setPlatformCardsData()
            // Search
            searchEdt
                .textChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                    changeSearchState(query)
                    presenter.searchInGames(query.toString())
                }



            genresAdapter.setOnItemClickListener {
                val direction = GamesFragmentDirections.actionToGamesfragment(
                    category = it.name,
                    id = it.id
                )
                findNavController().navigate(direction)
            }

            storesAdapter.setOnItemClickListener {
                val direction = GamesFragmentDirections.actionToGamesfragment(
                    category = it.name,
                    id = it.id
                )
                findNavController().navigate(direction)
            }

        }
    }

    private fun FragmentSearchBinding.changeSearchState(query: CharSequence) {
        if (query.toString().checkForEmptiness()) {
            contentSearchLayout.visibility = View.GONE
            searchResultLayout.visibility = View.VISIBLE
            // call api by presenter
        } else {
            contentSearchLayout.visibility = View.VISIBLE
            searchResultLayout.visibility = View.GONE
        }
    }

    private fun FragmentSearchBinding.setPlatformCardsData() {
        setOnClickListenerForPlatforms()
        psCard.loadByFade(PLAYSTATION_IMAGE_URL)
        xboxCard.loadByFade(XBOX_IMAGE_URL)
        pcCard.loadByFade(PC_IMAGE_URL)
        nintendoCard.loadByFade(NINTENDO_IMAGE_URL)
    }

    private fun FragmentSearchBinding.setOnClickListenerForPlatforms() {
        psCard.setOnClickListener {
            val direction = GamesFragmentDirections.actionToGamesfragment(
                category = Platform.PLAYSTATION.name,
                id = Platform.PLAYSTATION.id
            )
            findNavController().navigate(direction)
        }
        xboxCard.setOnClickListener {
            Snackbar.make(binding.root, "XBOX Selected", Snackbar.LENGTH_LONG).show()
        }
        pcCard.setOnClickListener {
            Snackbar.make(binding.root, "PC Selected", Snackbar.LENGTH_LONG).show()
        }
        nintendoCard.setOnClickListener {
            Snackbar.make(binding.root, "Nintendo Selected", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showStores(stores: List<ResponseStores.Result>) {
        storesAdapter.setData(stores)
        binding.storesRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = storesAdapter
        }
    }

    override fun showGenres(genres: List<ResponseStores.Result>) {
        binding.genresRecycler.apply {
            genresAdapter.setData(genres)
            adapter = genresAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }


    }

    override fun showSearchResult(games: List<ResponseGamesList.Result>) {
        games.forEach {
            Timber.tag(DEBUG_TAG).d("result: ${it.name} -> ${it.description}")
        }
        gameItemAdapter.setData(games)
        binding.searchRecycler.apply {
            adapter = gameItemAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun searchLoadingState(isShown: Boolean) {
        if (isShown) {
            binding.searchRecycler.visibility = View.GONE
            binding.searchProgress.visibility = View.VISIBLE
        } else {
            binding.searchRecycler.visibility = View.VISIBLE
            binding.searchProgress.visibility = View.GONE
        }
    }

    override fun showGeneralError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }



}