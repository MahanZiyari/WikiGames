package ir.mahan.wikigames.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.mahan.wikigames.R
import ir.mahan.wikigames.data.model.GameEntity
import ir.mahan.wikigames.data.model.ResponseGameDetails
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseScreenshots
import ir.mahan.wikigames.databinding.FragmentDetailsBinding
import ir.mahan.wikigames.ui.details.adapters.CardTextAdapter
import ir.mahan.wikigames.ui.details.adapters.ImageAdapter
import ir.mahan.wikigames.ui.home.adapter.SmallItemGameAdapter
import ir.mahan.wikigames.utils.QueryParam
import ir.mahan.wikigames.utils.loadByFade
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment : Fragment(), DetailsContract.View {

    // Binding Object
    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private var gameId: Int = 0

    @Inject
    lateinit var presenter: DetailsPresenter
    @Inject lateinit var genresAdapter: CardTextAdapter
    @Inject lateinit var franchiseAdapter: SmallItemGameAdapter
    @Inject lateinit var shotsAdapter: ImageAdapter
    @Inject
    lateinit var entity: GameEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameId = args.gameId
        presenter.getGameDetailsFromAPI(gameId)
        // Handle UI
        binding.apply {
            // Genres Config
            genresRecycler.apply {
                adapter = genresAdapter.also {
                    it.setOnItemClickListener {
                        findNavController().navigate(
                            DetailsFragmentDirections.actionToGamesfragment(
                                title = it.name,
                                id = it.id,
                                category = QueryParam.GENRES.name
                            )
                        )
                    }
                }
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            // ScreenShots
            screenshotsRecycler.apply {
                adapter = shotsAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            // franchise
            suggestRecycler.apply {
                adapter = franchiseAdapter.also {
                    it.setOnItemClickListener {
                        findNavController().navigate(
                            DetailsFragmentDirections.actionToDetailsfragment(it.id)
                        )
                    }
                }
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            // back Icon
            backImg.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun handleLoadingState(isShown: Boolean) {
        if (isShown) {
            binding.detailsScrollview.visibility = View.GONE
            binding.loadingDetails.visibility = View.VISIBLE
        } else {
            binding.detailsScrollview.visibility = View.VISIBLE
            binding.loadingDetails.visibility = View.GONE
        }
    }

    override fun showDetails(
        details: ResponseGameDetails,
        screenshots: List<ResponseScreenshots.Result>,
        franchise: List<ResponseGamesList.Result>
    ) {
        binding.apply {
            posterBigImg.load(details.backgroundImage)
            posterNormalImg.loadByFade(details.backgroundImage)
            gameNameTxt.text = details.name
            gameRateTxt.text = details.rating.toString()
            gameReleasedTxt.text = details.released
            gameSummaryInfo.text = details.descriptionRaw

            fillEntityFields(details)
            presenter.checkForFavorite(details.id)
        }
        franchiseAdapter.setData(franchise)
        shotsAdapter.setData(screenshots)
        genresAdapter.setData(details.genres)
    }

    private fun fillEntityFields(details: ResponseGameDetails) {
        entity.id = details.id
        entity.title = details.name
        entity.description = details.descriptionRaw
        entity.backgroundimage = details.backgroundImage
        entity.meta = details.metacritic.toString()
        entity.genres = details.genres.map { it.name }
    }

    override fun setFavoriteState(isAdded: Boolean) {
        binding.apply {
            //Click
            favImg.setOnClickListener {
                if (isAdded) {
                    presenter.removeGameFromFavorites(entity)
                } else {
                    presenter.addGameToFavorites(entity)
                }
            }
            //Change color
            if (isAdded) {
                favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.TigersEye))
            } else {
                favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }
    }

    override fun showGeneralError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}