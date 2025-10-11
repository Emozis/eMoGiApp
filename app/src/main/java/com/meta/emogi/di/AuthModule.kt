package com.meta.emogi.di

import android.content.Context
import com.meta.emogi.BuildConfig
import com.meta.emogi.data.repository.ApiRepository
import com.meta.emogi.feature.sync.data.impl.SessionRepository
import com.meta.emogi.feature.sync.data.impl.AppUpdateChecker
import com.meta.emogi.feature.sync.data.local.AppInternalDataSource
import com.meta.emogi.feature.sync.data.local.IAppInternalDataSource
import com.meta.emogi.feature.sync.domain.repo.IAppUpdateChecker
import com.meta.emogi.feature.sync.domain.repo.ISessionRepository
import com.meta.emogi.feature.sync.domain.usecase.CheckLoggedInUseCase
import com.meta.emogi.feature.sync.domain.usecase.CheckMandatoryUpdateUseCase
import com.meta.emogi.feature.sync.domain.usecase.GetTokenUseCase
import com.meta.emogi.util.ConfigUtil
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

// @Binds (본문 X, 구현체 1개만 파라미터)
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBindings {

    @Binds @Singleton
    abstract fun bindLocal(
        impl: AppInternalDataSource
    ): IAppInternalDataSource

    @Binds @Singleton
    abstract fun bindSessionRepo(
        impl: SessionRepository
    ): ISessionRepository

    @Binds @Singleton
    abstract fun bindAuthRepository(
        impl: com.meta.emogi.feature.login.data.AuthRepository
    ): com.meta.emogi.feature.login.domain.repo.IAuthRepository
}

// 값/외부 의존/Context 필요한 것: @Provides
@Module
@InstallIn(SingletonComponent::class)
object AuthProvides {

    @Provides @Singleton
    fun provideUpdateChecker(
        @ApplicationContext context: Context
    ): IAppUpdateChecker = AppUpdateChecker(context)

    // UseCase는 @Inject constructor 있으면 생략 가능. (원한다면 유지)
    @Provides
    fun provideCheckMandatoryUpdateUseCase(
        checker: IAppUpdateChecker
    ): CheckMandatoryUpdateUseCase = CheckMandatoryUpdateUseCase(checker)

    @Provides
    fun provideCheckLoggedInUseCase(
        repo: ISessionRepository
    ): CheckLoggedInUseCase = CheckLoggedInUseCase(repo)

    @Provides
    fun provideGetTokenUseCase(
        repo: ISessionRepository
    ): GetTokenUseCase = GetTokenUseCase(repo)

    @Provides @Named("appVersion")
    fun provideAppVersion(): String = BuildConfig.VERSION_NAME

    @Provides @Singleton
    fun provideApiRepository(): ApiRepository = ApiRepository()

    @Provides @Singleton
    fun provideConfigUtil(@ApplicationContext context: Context): ConfigUtil = ConfigUtil(context)

    @Provides @Named("oauthClientId")
    fun provideOAuthClientId(configUtil: ConfigUtil): String = configUtil.getProperty("OAUTH_CLIENT_ID")
}