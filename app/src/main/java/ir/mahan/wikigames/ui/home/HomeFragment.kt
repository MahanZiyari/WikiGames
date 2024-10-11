package ir.mahan.wikigames.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.databinding.FragmentHomeBinding
import ir.mahan.wikigames.ui.home.adapter.ImageAdapter
import ir.mahan.wikigames.ui.home.adapter.SmallItemGameAdapter
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContracts.View {

    // Binding Object
    private lateinit var binding: FragmentHomeBinding

    @Inject lateinit var bannerAdapter: ImageAdapter
    @Inject lateinit var bestGamesAdapter: SmallItemGameAdapter
    @Inject lateinit var bestShooterAdapter: SmallItemGameAdapter
    @Inject lateinit var presenter: HomePresenter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call APIs
        presenter.getLatestGames()
        presenter.getBestGamesByMetacritic()
        presenter.getBestOfShooter()
        // Handling UI
        binding.apply {

        }
    }

    override fun showLatestGamesOnCarousel(games: List<ResponseGamesList.Result>) {
        binding.banners.apply {
            val snapHelper = CarouselSnapHelper()
            snapHelper.attachToRecyclerView(this)
            bannerAdapter.setData(games)
            layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
            adapter = bannerAdapter
        }
    }

    override fun setCarouselLoadingState(isShown: Boolean) {
        if (isShown) {
            binding.banners.visibility = View.GONE
            binding.carouselProgressbar.visibility = View.VISIBLE
        } else {
            binding.banners.visibility = View.VISIBLE
            binding.carouselProgressbar.visibility = View.GONE
        }
    }

    override fun showBestGamesByMetacritic(games: List<ResponseGamesList.Result>) {
        binding.bestRatedGamesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            bestGamesAdapter.setData(games)
            adapter = bestGamesAdapter
        }
    }

    override fun showBestGamesShooter(games: List<ResponseGamesList.Result>) {
        binding.bestShootingRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            bestShooterAdapter.setData(games)
            adapter = bestShooterAdapter
        }
    }

    override fun showGeneralError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}