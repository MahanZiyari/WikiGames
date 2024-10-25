package ir.mahan.wikigames.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.mahan.wikigames.data.model.ResponseGameDetails
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.data.model.ResponseScreenshots
import ir.mahan.wikigames.databinding.FragmentDetailsBinding
import ir.mahan.wikigames.ui.search.adapter.ImageAdapter
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
    @Inject lateinit var franchiseAdapter: ImageAdapter

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
        presenter.getDetailsForGame(gameId)
        // Handle UI
        binding.apply {
            simGamesLay.visibility = View.GONE
            genresRecycler.apply {
                adapter = genresAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            // back Icon
            backImg.setOnClickListener {
                findNavController().navigateUp()
            }
            // Add to favorites
            favImg.setOnClickListener {
                Toast.makeText(requireContext(), "favorites", Toast.LENGTH_SHORT).show()
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
        }

        genresAdapter.setData(details.genres)
    }

    override fun showGeneralError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}