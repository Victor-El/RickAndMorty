package com.turingheights.rickandmorty.di

import com.turingheights.domain.repositories.CharacterRepository
import com.turingheights.domain.usecases.GetCharactersUseCase
import com.turingheights.domain.usecases.GetFavouritesUseCase
import com.turingheights.domain.usecases.UpdateCharacterUseCase
import com.turingheights.core.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return Navigator()
    }

    @Singleton
    @Provides
    fun provideListUseCase(
        repository: CharacterRepository,
    ): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideFavouriteUseCase(
        repository: CharacterRepository,
    ): GetFavouritesUseCase {
        return GetFavouritesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateCharacterUseCase(
        repository: CharacterRepository,
    ): UpdateCharacterUseCase {
        return UpdateCharacterUseCase(repository)
    }

}