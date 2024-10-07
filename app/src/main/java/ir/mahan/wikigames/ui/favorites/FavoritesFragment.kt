package ir.mahan.wikigames.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.mahan.wikigames.R
import ir.mahan.wikigames.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    // Binding Object
    private lateinit var binding: FragmentFavoritesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Handling UI
    }


}