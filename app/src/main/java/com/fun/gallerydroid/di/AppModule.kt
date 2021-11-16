package com.`fun`.gallerydroid.di

import com.`fun`.gallerydroid.common.Constants
import com.`fun`.gallerydroid.data.remote.UserAlbumsApi
import com.`fun`.gallerydroid.data.repository.UserRepositoryImpl
import com.`fun`.gallerydroid.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): UserAlbumsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAlbumsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserAlbumsApi): UserRepository {
        return UserRepositoryImpl(api)
    }
}