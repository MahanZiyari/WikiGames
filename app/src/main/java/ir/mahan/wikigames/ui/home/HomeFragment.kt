package ir.mahan.wikigames.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.mahan.wikigames.data.model.ResponseGamesList
import ir.mahan.wikigames.databinding.FragmentHomeBinding
import ir.mahan.wikigames.ui.home.adapter.CarouselAdapter
import ir.mahan.wikigames.ui.home.adapter.SmallItemGameAdapter
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContracts.View {

    // Binding Object
    private lateinit var binding: FragmentHomeBinding

    @Inject lateinit var bannerAdapter: CarouselAdapter
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
        presenter.getAllData()
        // Handling UI

    }

    override fun showAllData(data: Map<Int, List<ResponseGamesList.Result>>) {
        bannerAdapter.setData(data.get(1)!!)
        bestGamesAdapter.setData(data.get(2)!!)
        bestShooterAdapter.setData(data.get(3)!!)
        binding.apply {
            // Carousel Config
            banners.apply {
                val snapHelper = CarouselSnapHelper()
                if (onFlingListener == null)
                    snapHelper.attachToRecyclerView(this)
                layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
                adapter = bannerAdapter
            }
            // Best Scores Config
            bestRatedGamesRecycler.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = bestGamesAdapter
            }
            // Best of A Genres
            bestShootingRecycler.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = bestShooterAdapter
            }
            // Click Listeners
            val clickEvent: ((ResponseGamesList.Result) -> Unit) = {
                findNavController().navigate(
                    HomeFragmentDirections.actionToDetailsfragment(it.id)
                )
            }
            bestGamesAdapter.setOnItemClickListener {
                clickEvent(it)
            }

            bestShooterAdapter.setOnItemClickListener {
                clickEvent(it)
            }

            bannerAdapter.setOnItemClickListener {
                clickEvent(it)
            }

        }
    }

    override fun showLatestGamesOnCarousel(games: List<ResponseGamesList.Result>) {
        binding.banners.apply {
            val snapHelper = CarouselSnapHelper()
            if (onFlingListener == null)
                snapHelper.attachToRecyclerView(this)
            bannerAdapter.setData(games)
            layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
            adapter = bannerAdapter
        }
    }

    override fun setLoadingState(isShown: Boolean) {
        if (isShown) {
            binding.contentLayout.visibility = View.GONE
            binding.loadingProgress.visibility = View.VISIBLE
        } else {
            binding.contentLayout.visibility = View.VISIBLE
            binding.loadingProgress.visibility = View.GONE
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