package ir.mahan.wikigames.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.mahan.wikigames.data.model.ResponseStores
import ir.mahan.wikigames.databinding.FragmentSearchBinding
import ir.mahan.wikigames.utils.NINTENDO_IMAGE_URL
import ir.mahan.wikigames.utils.PC_IMAGE_URL
import ir.mahan.wikigames.utils.PLAYSTATION_IMAGE_URL
import ir.mahan.wikigames.utils.XBOX_IMAGE_URL
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment(), SearchContracts.View {

    // Binding Object
    private lateinit var binding: FragmentSearchBinding

    @Inject lateinit var presenter: SearchPresenter
    @Inject lateinit var storesAdapter: ImageAdapter
    @Inject lateinit var genresAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Call Api
        presenter.getAllGenres()
        presenter.getAllStores()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handling UI
        binding.apply {
            setPlatformCardsData()
            setOnClickListenerForPlatforms()
        }
    }

    private fun FragmentSearchBinding.setPlatformCardsData() {
        psCard.load(PLAYSTATION_IMAGE_URL)
        xboxCard.load(XBOX_IMAGE_URL)
        pcCard.load(PC_IMAGE_URL)
        nintendoCard.load(NINTENDO_IMAGE_URL)
    }

    private fun FragmentSearchBinding.setOnClickListenerForPlatforms() {
        psCard.setOnClickListener {
            Snackbar.make(binding.root, "PlayStation Selected", Snackbar.LENGTH_LONG).show()
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
        binding.storesRecycler.apply {
            storesAdapter.setData(stores)
            adapter = storesAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun showGenres(genres: List<ResponseStores.Result>) {
        binding.genresRecycler.apply {
            genresAdapter.setData(genres)
            adapter = genresAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun showGeneralError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}