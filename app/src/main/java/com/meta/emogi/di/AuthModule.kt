package com.meta.emogi.di

import android.content.Context
import com.meta.emogi.data.auth.local.SessionLocalDataSource
import com.meta.emogi.data.auth.local.UserPreferenceLocalDataSource
import com.meta.emogi.data.auth.repo.SessionRepositoryImpl
import com.meta.emogi.domain.auth.repo.SessionRepository
import com.meta.emogi.domain.auth.usecase.CheckLoggedInUseCase
import com.meta.emogi.domain.auth.usecase.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides @Singleton
    fun provideLocal(@ApplicationContext context: Context): SessionLocalDataSource =
        UserPreferenceLocalDataSource(context)

    @Provides @Singleton
    fun provideRepo(local: SessionLocalDataSource): SessionRepository =
        SessionRepositoryImpl(local)

    @Provides
    fun provideCheckLoggedInUseCase(repo: SessionRepository): CheckLoggedInUseCase =
        CheckLoggedInUseCase(repo)

    @Provides
    fun provideGetTokenUseCase(repo: SessionRepository): GetTokenUseCase =
        GetTokenUseCase(repo)
}