package ir.mahan.wikigames.utils.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ir.mahan.wikigames.ui.details.DetailsContract
import ir.mahan.wikigames.ui.games.GamesContract
import ir.mahan.wikigames.ui.home.HomeContracts
import ir.mahan.wikigames.ui.search.SearchContracts

@Module
@InstallIn(FragmentComponent::class)
object ContractsModule {

    @Provides
    fun provideHomeContracts(fragment: Fragment): HomeContracts.View =
        fragment as HomeContracts.View

    @Provides
    fun provideSearchContracts(fragment: Fragment): SearchContracts.View =
        fragment as SearchContracts.View

    @Provides
    fun provideGamesContracts(fragment: Fragment): GamesContract.View =
        fragment as GamesContract.View

    @Provides
    fun provideGDetailsContracts(fragment: Fragment): DetailsContract.View =
        fragment as DetailsContract.View

}