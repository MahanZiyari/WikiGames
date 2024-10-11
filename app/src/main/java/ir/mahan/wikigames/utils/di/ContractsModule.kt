package ir.mahan.wikigames.utils.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ir.mahan.wikigames.ui.home.HomeContracts

@Module
@InstallIn(FragmentComponent::class)
object ContractsModule {

    @Provides
    fun provideHomeContracts(fragment: Fragment): HomeContracts.View =
        fragment as HomeContracts.View
}