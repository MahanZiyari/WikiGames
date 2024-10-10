package ir.mahan.wikigames.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.carousel.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.mahan.wikigames.R
import ir.mahan.wikigames.databinding.FragmentHomeBinding
import ir.mahan.wikigames.ui.home.adapter.ImageAdapter
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    // Binding Object
    private lateinit var binding: FragmentHomeBinding

    @Inject lateinit var bannerAdapter: ImageAdapter


    val testImages = arrayListOf(
        "https://media.rawg.io/media/games/b7b/b7b8381707152afc7d91f5d95de70e39.jpg",
        "https://media.rawg.io/media/games/0be/0bea0a08a4d954337305391b778a7f37.jpg",
        "https://media.rawg.io/media/games/b6b/b6b20bfc4b34e312dbc8aac53c95a348.jpg",
        "https://media.rawg.io/media/games/530/5302dd22a190e664531236ca724e8726.jpg"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Handling UI
        binding.apply {
            banners.apply {
                bannerAdapter.setData(testImages)
                adapter = bannerAdapter
            }
        }
    }

}